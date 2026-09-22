package com.dosw.bluevelvet.service.vehiculo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoRequestDTO;
import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoResponseDTO;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.vehiculo.RegistroVehiculoMapperIn;
import com.dosw.bluevelvet.mapper.vehiculo.RegistroVehiculoMapperOut;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.RegistroVehiculoValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistroVehiculoServiceImpl implements IRegistroVehiculoService {

    private final Map<Long, RegistroVehiculo> registros = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final RegistroVehiculoMapperIn mapperIn;
    private final RegistroVehiculoMapperOut mapperOut;
    private final RegistroVehiculoValidator validator;

    @Override
    public List<RegistroVehiculoResponseDTO> obtenerTodos() {
        return registros.values().stream()
                .map(mapperOut::toDTO)
                .toList();
    }

    @Override
    public RegistroVehiculoResponseDTO buscarPorId(Long id) {
        log.debug("Buscando registro de vehiculo con id={}", id);
        return mapperOut.toDTO(buscarOLanzar(id));
    }

    @Override
    public RegistroVehiculoResponseDTO registrarEntrada(RegistroVehiculoRequestDTO dto) {
        log.info("Registrando entrada del vehiculo con placa '{}'", dto.placa());
        validator.validarSinRegistroActivo(registros, dto.placa());

        RegistroVehiculo registro = mapperIn.toDomain(dto);
        registro.setId(IdGenerator.siguiente(contadorId));
        registros.put(registro.getId(), registro);

        log.info("Registro id={} creado para la placa '{}'", registro.getId(), registro.getPlaca());
        return mapperOut.toDTO(registro);
    }

    private RegistroVehiculo buscarOLanzar(Long id) {
        return Optional.ofNullable(registros.get(id))
                .orElseThrow(() -> new RecursoNoEncontradoException("Registro de vehiculo no encontrado: " + id));
    }
}
