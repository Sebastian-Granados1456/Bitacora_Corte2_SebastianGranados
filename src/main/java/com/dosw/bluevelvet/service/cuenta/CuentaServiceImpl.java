package com.dosw.bluevelvet.service.cuenta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.dto.cuenta.CuentaRequestDTO;
import com.dosw.bluevelvet.dto.cuenta.CuentaResponseDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.cuenta.CuentaMapperOut;
import com.dosw.bluevelvet.model.domain.Cuenta;
import com.dosw.bluevelvet.model.domain.EstadoCuenta;
import com.dosw.bluevelvet.model.domain.ItemPedido;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.service.pedido.IPedidoService;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.CuentaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CuentaServiceImpl implements ICuentaService {

    private final Map<Long, Cuenta> cuentas = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final CuentaMapperOut mapperOut;
    private final CuentaValidator cuentaValidator;
    private final IMesaService mesaService;
    private final IPedidoService pedidoService;

    @Override
    public List<CuentaResponseDTO> obtenerTodas() {
        return cuentas.values().stream()
                .map(this::toDTOConPedidosActualizados)
                .toList();
    }

    @Override
    public CuentaResponseDTO buscarPorId(Long id) {
        log.debug("Buscando cuenta con id={}", id);
        return toDTOConPedidosActualizados(buscarOLanzar(id));
    }

    @Override
    public CuentaResponseDTO abrir(CuentaRequestDTO dto) {
        log.info("Abriendo cuenta para la mesa {}", dto.idMesa());

        // Verifica que la mesa exista (delega en IMesaService)
        mesaService.buscarPorId(dto.idMesa());
        cuentaValidator.validarSinCuentaAbierta(cuentas, dto.idMesa());

        Cuenta cuenta = new Cuenta();
        cuenta.setId(IdGenerator.siguiente(contadorId));
        cuenta.setIdMesa(dto.idMesa());
        cuenta.setEstado(EstadoCuenta.ABIERTA);
        cuenta.setFechaApertura(LocalDateTime.now());
        cuentas.put(cuenta.getId(), cuenta);

        mesaService.marcarCuentaAbierta(dto.idMesa(), true);

        log.info("Cuenta id={} abierta para la mesa {}", cuenta.getId(), dto.idMesa());
        return toDTOConPedidosActualizados(cuenta);
    }

    private Cuenta buscarOLanzar(Long id) {
        return Optional.ofNullable(cuentas.get(id))
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta no encontrada: " + id));
    }

    /**
     * Sincroniza los pedidos de la mesa (obtenidos via IPedidoService) con
     * la cuenta antes de calcular el total, para no duplicar el estado del
     * pedido dentro de la cuenta.
     */
    private CuentaResponseDTO toDTOConPedidosActualizados(Cuenta cuenta) {
        List<Pedido> pedidosDeLaMesa = pedidoService.obtenerPorMesa(cuenta.getIdMesa()).stream()
                .map(this::toPedidoDomain)
                .toList();
        cuenta.setPedidos(pedidosDeLaMesa);
        return mapperOut.toDTO(cuenta);
    }

    // Adaptador minimo para reutilizar Cuenta.calcularTotal() sin duplicar
    // la logica de sumatoria de items.
    private Pedido toPedidoDomain(PedidoResponseDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setId(dto.id());
        pedido.setIdMesa(dto.idMesa());
        pedido.setItems(dto.items().stream()
                .map(i -> new ItemPedido(i.idPlato(), i.nombrePlato(), i.precioCongelado(), i.cantidad()))
                .toList());
        return pedido;
    }
}
