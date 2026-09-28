package com.dosw.bluevelvet.validator.reserva;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Regla de negocio propia de las reservas de Blue Velvet. Ya no carga todas
 * las reservas a memoria: le pregunta al Repository.
 */
@Component
@RequiredArgsConstructor
public class ReservaValidator implements IReservaValidator {

    private final ReservaRepository reservaRepository;

    @Override
    public void validarSinCruceDeHorario(Long idMesa, LocalDateTime fechaHora) {
        if (reservaRepository.existsByMesaIdAndFechaHora(idMesa, fechaHora)) {
            throw new RecursoDuplicadoException(
                    "Ya existe una reserva para la mesa " + idMesa + " en ese horario");
        }
    }
}
