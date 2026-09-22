package com.dosw.bluevelvet.service.mesa;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.mesa.IMesaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MesaServiceImpl implements IMesaService {

    private final Map<Long, Mesa> mesas = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final IMesaValidator validator;

    @Override
    public List<Mesa> obtenerTodas() {
        return mesas.values().stream().toList();
    }

    @Override
    public Mesa obtenerPorId(Long id) {
        return mesas.values().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Mesa no encontrada: id={}", id);
                    return new RecursoNoEncontradoException("Mesa no encontrada: " + id);
                });
    }

    @Override
    public Mesa crear(Mesa mesa) {
        validator.validarNumeroUnico(mesa.getNumero(), mesas.values());
        mesa.setId(IdGenerator.siguiente(contadorId));
        mesas.put(mesa.getId(), mesa);
        log.info("Mesa numero {} creada con id={}", mesa.getNumero(), mesa.getId());
        return mesa;
    }

    @Override
    public void marcarCuentaAbierta(Long id, boolean abierta) {
        Mesa mesa = obtenerPorId(id);
        if (abierta) {
            mesa.abrirCuenta();
        } else {
            mesa.cerrarCuenta();
        }
        log.info("Mesa id={} cuentaAbierta={}", id, abierta);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id);
        mesas.remove(id);
        log.info("Mesa id={} eliminada", id);
    }
}
