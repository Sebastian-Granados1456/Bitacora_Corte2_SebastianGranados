package com.dosw.bluevelvet.validator.reserva;

import java.time.LocalDateTime;
import java.util.Collection;

import com.dosw.bluevelvet.model.domain.Reserva;

public interface IReservaValidator {

    void validarSinCruceDeHorario(Long idMesa, LocalDateTime fechaHora, Collection<Reserva> reservasExistentes);
}
