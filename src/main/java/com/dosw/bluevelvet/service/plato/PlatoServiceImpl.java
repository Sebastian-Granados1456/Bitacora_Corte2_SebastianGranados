package com.dosw.bluevelvet.service.plato;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.entity.PlatoEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.plato.PlatoEntityMapper;
import com.dosw.bluevelvet.model.domain.Plato;
import com.dosw.bluevelvet.repository.PlatoRepository;
import com.dosw.bluevelvet.validator.plato.IPlatoValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatoServiceImpl implements IPlatoService {

    private final PlatoRepository platoRepository;
    private final PlatoEntityMapper entityMapper;
    private final IPlatoValidator validator;

    @Override
    public List<Plato> obtenerTodos() {
        List<PlatoEntity> entidades = platoRepository.findAll();
        log.info("Obteniendo todos los platos. Total: {}", entidades.size());
        return entityMapper.toDomainList(entidades);
    }

    @Override
    public Plato obtenerPorId(Long id) {
        PlatoEntity entidad = platoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Plato no encontrado: id={}", id);
                    return new RecursoNoEncontradoException("Plato no encontrado: " + id);
                });
        return entityMapper.toDomain(entidad);
    }

    @Override
    public Plato crear(Plato plato) {
        validator.validarNombreUnico(plato.getNombre());

        PlatoEntity entidad = entityMapper.toEntity(plato);
        PlatoEntity guardada = platoRepository.save(entidad);

        log.info("Plato creado: id={}, nombre={}", guardada.getId(), guardada.getNombre());
        return entityMapper.toDomain(guardada);
    }

    @Override
    public void eliminar(Long id) {
        if (!platoRepository.existsById(id)) {
            log.warn("Plato no encontrado: id={}", id);
            throw new RecursoNoEncontradoException("Plato no encontrado: " + id);
        }
        platoRepository.deleteById(id);
        log.info("Plato eliminado: id={}", id);
    }
}
