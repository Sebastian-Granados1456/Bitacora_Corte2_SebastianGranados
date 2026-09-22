package com.dosw.bluevelvet.service.plato;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.Plato;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.plato.IPlatoValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatoServiceImpl implements IPlatoService {

    private final Map<Long, Plato> platos = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final IPlatoValidator validator;

    @Override
    public List<Plato> obtenerTodos() {
        log.info("Obteniendo todos los platos. Total: {}", platos.size());
        return platos.values().stream().toList();
    }

    @Override
    public Plato obtenerPorId(Long id) {
        return platos.values().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Plato no encontrado: id={}", id);
                    return new RecursoNoEncontradoException("Plato no encontrado: " + id);
                });
    }

    @Override
    public Plato crear(Plato plato) {
        validator.validarNombreUnico(plato.getNombre(), platos.values());
        plato.setId(IdGenerator.siguiente(contadorId));
        platos.put(plato.getId(), plato);
        log.info("Plato creado: id={}, nombre={}", plato.getId(), plato.getNombre());
        return plato;
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id);
        platos.remove(id);
        log.info("Plato eliminado: id={}", id);
    }
}
