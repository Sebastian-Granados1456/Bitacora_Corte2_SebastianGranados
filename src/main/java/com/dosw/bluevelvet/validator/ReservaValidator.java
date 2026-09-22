package com.dosw.bluevelvet.validator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Reserva;

/**
 * Regla de negocio propia de las reservas de Blue Velvet.
 */
@Component
public class ReservaValidator {

    /**
     * Una mesa no puede tener dos reservas con la misma fecha y hora.
     */
    public void validarSinCruceDeHorario(Map<Long, Reserva> reservas, Long idMesa, java.time.LocalDateTime fechaHora) {
        boolean existeCruce = reservas.values().stream()
                .filter(r -> r.getIdMesa().equals(idMesa))
                .anyMatch(r -> r.getFechaHora().equals(fechaHora));

        if (existeCruce) {
            throw new RecursoDuplicadoException(
                    "Ya existe una reserva para la mesa " + idMesa + " en ese horario");
        }
    }
}
