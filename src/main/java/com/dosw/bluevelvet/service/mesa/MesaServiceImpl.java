package com.dosw.bluevelvet.service.mesa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.mesa.MesaEntityMapper;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.repository.MesaRepository;
import com.dosw.bluevelvet.validator.mesa.IMesaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MesaServiceImpl implements IMesaService {

    private final MesaRepository mesaRepository;
    private final MesaEntityMapper entityMapper;
    private final IMesaValidator validator;

    @Override
    public List<Mesa> obtenerTodas() {
        return entityMapper.toDomainList(mesaRepository.findAll());
    }

    @Override
    public Mesa obtenerPorId(Long id) {
        MesaEntity entidad = buscarEntidadOLanzar(id);
        return entityMapper.toDomain(entidad);
    }

    @Override
    public Mesa crear(Mesa mesa) {
        validator.validarNumeroUnico(mesa.getNumero());

        MesaEntity entidad = entityMapper.toEntity(mesa);
        MesaEntity guardada = mesaRepository.save(entidad);

        log.info("Mesa numero {} creada con id={}", guardada.getNumero(), guardada.getId());
        return entityMapper.toDomain(guardada);
    }

    @Override
    public void marcarCuentaAbierta(Long id, boolean abierta) {
        MesaEntity entidad = buscarEntidadOLanzar(id);

        // Aplica la logica de negocio del dominio antes de volver a guardar
        Mesa mesa = entityMapper.toDomain(entidad);
        if (abierta) {
            mesa.abrirCuenta();
        } else {
            mesa.cerrarCuenta();
        }

        mesaRepository.save(entityMapper.toEntity(mesa));
        log.info("Mesa id={} cuentaAbierta={}", id, abierta);
    }

    @Override
    public void eliminar(Long id) {
        if (!mesaRepository.existsById(id)) {
            log.warn("Mesa no encontrada: id={}", id);
            throw new RecursoNoEncontradoException("Mesa no encontrada: " + id);
        }
        mesaRepository.deleteById(id);
        log.info("Mesa id={} eliminada", id);
    }

    private MesaEntity buscarEntidadOLanzar(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Mesa no encontrada: id={}", id);
                    return new RecursoNoEncontradoException("Mesa no encontrada: " + id);
                });
    }
}
