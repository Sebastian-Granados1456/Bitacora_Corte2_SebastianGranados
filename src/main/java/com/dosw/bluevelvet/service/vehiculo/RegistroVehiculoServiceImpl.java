package com.dosw.bluevelvet.service.vehiculo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.entity.RegistroVehiculoEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.vehiculo.RegistroVehiculoEntityMapper;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;
import com.dosw.bluevelvet.repository.RegistroVehiculoRepository;
import com.dosw.bluevelvet.validator.vehiculo.IRegistroVehiculoValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistroVehiculoServiceImpl implements IRegistroVehiculoService {

    private final RegistroVehiculoRepository registroVehiculoRepository;
    private final RegistroVehiculoEntityMapper entityMapper;
    private final IRegistroVehiculoValidator validator;

    @Override
    public List<RegistroVehiculo> obtenerTodos() {
        return entityMapper.toDomainList(registroVehiculoRepository.findAll());
    }

    @Override
    public RegistroVehiculo obtenerPorId(Long id) {
        RegistroVehiculoEntity entidad = buscarEntidadOLanzar(id);
        return entityMapper.toDomain(entidad);
    }

    @Override
    public RegistroVehiculo registrarEntrada(RegistroVehiculo registro) {
        log.info("Registrando entrada del vehiculo con placa '{}'", registro.getPlaca());
        validator.validarSinRegistroActivo(registro.getPlaca());

        RegistroVehiculoEntity entidad = entityMapper.toEntity(registro);
        RegistroVehiculoEntity guardada = registroVehiculoRepository.save(entidad);

        log.info("Registro id={} creado para la placa '{}'", guardada.getId(), guardada.getPlaca());
        return entityMapper.toDomain(guardada);
    }

    private RegistroVehiculoEntity buscarEntidadOLanzar(Long id) {
        return registroVehiculoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Registro de vehiculo no encontrado: id={}", id);
                    return new RecursoNoEncontradoException("Registro de vehiculo no encontrado: " + id);
                });
    }
}
