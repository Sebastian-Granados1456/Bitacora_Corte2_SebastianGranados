package com.dosw.bluevelvet.service.vehiculo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.vehiculo.IRegistroVehiculoValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistroVehiculoServiceImpl implements IRegistroVehiculoService {

    private final Map<Long, RegistroVehiculo> registros = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final IRegistroVehiculoValidator validator;

    @Override
    public List<RegistroVehiculo> obtenerTodos() {
        return registros.values().stream().toList();
    }

    @Override
    public RegistroVehiculo obtenerPorId(Long id) {
        return registros.values().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Registro de vehiculo no encontrado: id={}", id);
                    return new RecursoNoEncontradoException("Registro de vehiculo no encontrado: " + id);
                });
    }

    @Override
    public RegistroVehiculo registrarEntrada(RegistroVehiculo registro) {
        log.info("Registrando entrada del vehiculo con placa '{}'", registro.getPlaca());
        validator.validarSinRegistroActivo(registro.getPlaca(), registros.values());

        registro.setId(IdGenerator.siguiente(contadorId));
        registros.put(registro.getId(), registro);

        log.info("Registro id={} creado para la placa '{}'", registro.getId(), registro.getPlaca());
        return registro;
    }
}
