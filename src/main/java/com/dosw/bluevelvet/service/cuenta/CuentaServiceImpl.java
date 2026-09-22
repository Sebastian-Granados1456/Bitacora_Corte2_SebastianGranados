package com.dosw.bluevelvet.service.cuenta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.Cuenta;
import com.dosw.bluevelvet.model.domain.EstadoCuenta;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.service.pedido.IPedidoService;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.cuenta.ICuentaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CuentaServiceImpl implements ICuentaService {

    private final Map<Long, Cuenta> cuentas = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final ICuentaValidator validator;
    private final IMesaService mesaService;
    private final IPedidoService pedidoService;

    @Override
    public List<Cuenta> obtenerTodas() {
        return cuentas.values().stream()
                .map(this::conPedidosActualizados)
                .toList();
    }

    @Override
    public Cuenta obtenerPorId(Long id) {
        return conPedidosActualizados(buscarOLanzar(id));
    }

    @Override
    public Cuenta abrir(Long idMesa) {
        log.info("Abriendo cuenta para la mesa {}", idMesa);

        // Un Service puede depender de otro Service, siempre via interfaz
        mesaService.obtenerPorId(idMesa);
        validator.validarSinCuentaAbierta(idMesa, cuentas.values());

        Cuenta cuenta = Cuenta.builder()
                .id(IdGenerator.siguiente(contadorId))
                .idMesa(idMesa)
                .estado(EstadoCuenta.ABIERTA)
                .fechaApertura(LocalDateTime.now())
                .build();
        cuentas.put(cuenta.getId(), cuenta);

        mesaService.marcarCuentaAbierta(idMesa, true);

        log.info("Cuenta id={} abierta para la mesa {}", cuenta.getId(), idMesa);
        return conPedidosActualizados(cuenta);
    }

    private Cuenta buscarOLanzar(Long id) {
        Cuenta cuenta = cuentas.get(id);
        if (cuenta == null) {
            log.warn("Cuenta no encontrada: id={}", id);
            throw new RecursoNoEncontradoException("Cuenta no encontrada: " + id);
        }
        return cuenta;
    }

    /**
     * Sincroniza los pedidos de la mesa (obtenidos via IPedidoService, que
     * ya devuelve objetos de dominio) con la cuenta antes de calcular el
     * total.
     */
    private Cuenta conPedidosActualizados(Cuenta cuenta) {
        List<Pedido> pedidosDeLaMesa = pedidoService.obtenerPorMesa(cuenta.getIdMesa());
        cuenta.setPedidos(pedidosDeLaMesa);
        return cuenta;
    }
}
