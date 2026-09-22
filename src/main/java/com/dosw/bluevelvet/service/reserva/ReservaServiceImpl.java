package com.dosw.bluevelvet.service.reserva;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.Reserva;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.reserva.IReservaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservaServiceImpl implements IReservaService {

    private final Map<Long, Reserva> reservas = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final IReservaValidator validator;
    private final IMesaService mesaService;

    @Override
    public List<Reserva> obtenerTodas() {
        return reservas.values().stream().toList();
    }

    @Override
    public Reserva obtenerPorId(Long id) {
        return reservas.values().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Reserva no encontrada: id={}", id);
                    return new RecursoNoEncontradoException("Reserva no encontrada: " + id);
                });
    }

    @Override
    public Reserva crear(Reserva reserva) {
        log.info("Creando reserva para la mesa {} a nombre de '{}'", reserva.getIdMesa(), reserva.getCliente());

        // Verifica que la mesa exista (delega en IMesaService: un Service
        // puede depender de otro Service, siempre via interfaz)
        mesaService.obtenerPorId(reserva.getIdMesa());
        validator.validarSinCruceDeHorario(reserva.getIdMesa(), reserva.getFechaHora(), reservas.values());

        reserva.setId(IdGenerator.siguiente(contadorId));
        reservas.put(reserva.getId(), reserva);

        log.info("Reserva creada con id={} para la mesa {}", reserva.getId(), reserva.getIdMesa());
        return reserva;
    }

    @Override
    public void cancelar(Long id) {
        obtenerPorId(id);
        reservas.remove(id);
        log.info("Reserva id={} cancelada", id);
    }
}
