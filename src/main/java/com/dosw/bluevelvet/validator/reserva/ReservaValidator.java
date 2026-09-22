package com.dosw.bluevelvet.validator.reserva;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Reserva;

/**
 * Regla de negocio propia de las reservas de Blue Velvet.
 */
@Component
public class ReservaValidator implements IReservaValidator {

    @Override
    public void validarSinCruceDeHorario(Long idMesa, LocalDateTime fechaHora, Collection<Reserva> reservasExistentes) {
        boolean existeCruce = reservasExistentes.stream()
                .filter(r -> r.getIdMesa().equals(idMesa))
                .anyMatch(r -> r.getFechaHora().equals(fechaHora));

        if (existeCruce) {
            throw new RecursoDuplicadoException(
                    "Ya existe una reserva para la mesa " + idMesa + " en ese horario");
        }
    }
}
