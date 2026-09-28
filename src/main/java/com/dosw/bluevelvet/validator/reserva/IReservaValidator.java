package com.dosw.bluevelvet.validator.reserva;

import java.time.LocalDateTime;

public interface IReservaValidator {

    void validarSinCruceDeHorario(Long idMesa, LocalDateTime fechaHora);
}
