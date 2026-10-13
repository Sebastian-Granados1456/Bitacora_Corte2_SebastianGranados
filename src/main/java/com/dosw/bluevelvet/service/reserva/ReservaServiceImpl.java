package com.dosw.bluevelvet.service.reserva;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.entity.ReservaEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.reserva.ReservaEntityMapper;
import com.dosw.bluevelvet.model.domain.Reserva;
import com.dosw.bluevelvet.repository.ReservaRepository;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.validator.reserva.IReservaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservaServiceImpl implements IReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaEntityMapper entityMapper;
    private final IReservaValidator validator;
    private final IMesaService mesaService;

    @Override
    public List<Reserva> obtenerTodas() {
        return entityMapper.toDomainList(reservaRepository.findAll());
    }

    @Override
    public Reserva obtenerPorId(Long id) {
        ReservaEntity entidad = buscarEntidadOLanzar(id);
        return entityMapper.toDomain(entidad);
    }

    @Override
    public Reserva crear(Reserva reserva) {
        log.info("Creando reserva para la mesa {} a nombre de '{}'", reserva.getIdMesa(), reserva.getCliente());

        mesaService.obtenerPorId(reserva.getIdMesa());
        validator.validarSinCruceDeHorario(reserva.getIdMesa(), reserva.getFechaHora());

        ReservaEntity entidad = entityMapper.toEntity(reserva);
        ReservaEntity guardada = reservaRepository.save(entidad);

        log.info("Reserva creada con id={} para la mesa {}", guardada.getId(), reserva.getIdMesa());
        return entityMapper.toDomain(guardada);
    }

    @Override
    public void cancelar(Long id) {
        if (!reservaRepository.existsById(id)) {
            log.warn("Reserva no encontrada: id={}", id);
            throw new RecursoNoEncontradoException("Reserva no encontrada: " + id);
        }
        reservaRepository.deleteById(id);
        log.info("Reserva id={} cancelada", id);
    }

    private ReservaEntity buscarEntidadOLanzar(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reserva no encontrada: id={}", id);
                    return new RecursoNoEncontradoException("Reserva no encontrada: " + id);
                });
    }
}
