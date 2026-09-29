package com.dosw.bluevelvet.service.cuenta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.entity.CuentaEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.cuenta.CuentaEntityMapper;
import com.dosw.bluevelvet.model.domain.Cuenta;
import com.dosw.bluevelvet.model.domain.EstadoCuenta;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.repository.CuentaRepository;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.service.pedido.IPedidoService;
import com.dosw.bluevelvet.validator.cuenta.ICuentaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CuentaServiceImpl implements ICuentaService {

    private final CuentaRepository cuentaRepository;
    private final CuentaEntityMapper entityMapper;
    private final ICuentaValidator validator;
    private final IMesaService mesaService;
    private final IPedidoService pedidoService;

    @Override
    public List<Cuenta> obtenerTodas() {
        return entityMapper.toDomainList(cuentaRepository.findAll()).stream()
                .map(this::conPedidosActualizados)
                .toList();
    }

    @Override
    public Cuenta obtenerPorId(Long id) {
        CuentaEntity entidad = buscarEntidadOLanzar(id);
        return conPedidosActualizados(entityMapper.toDomain(entidad));
    }

    @Override
    public Cuenta abrir(Long idMesa) {
        log.info("Abriendo cuenta para la mesa {}", idMesa);

        mesaService.obtenerPorId(idMesa);
        validator.validarSinCuentaAbierta(idMesa);

        Cuenta cuenta = Cuenta.builder()
                .idMesa(idMesa)
                .estado(EstadoCuenta.ABIERTA)
                .fechaApertura(LocalDateTime.now())
                .build();

        CuentaEntity guardada = cuentaRepository.save(entityMapper.toEntity(cuenta));
        mesaService.marcarCuentaAbierta(idMesa, true);

        log.info("Cuenta id={} abierta para la mesa {}", guardada.getId(), idMesa);
        return conPedidosActualizados(entityMapper.toDomain(guardada));
    }

    private CuentaEntity buscarEntidadOLanzar(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cuenta no encontrada: id={}", id);
                    return new RecursoNoEncontradoException("Cuenta no encontrada: " + id);
                });
    }

    private Cuenta conPedidosActualizados(Cuenta cuenta) {
        List<Pedido> pedidosDeLaMesa = pedidoService.obtenerPorMesa(cuenta.getIdMesa());
        cuenta.setPedidos(pedidosDeLaMesa);
        return cuenta;
    }
}
