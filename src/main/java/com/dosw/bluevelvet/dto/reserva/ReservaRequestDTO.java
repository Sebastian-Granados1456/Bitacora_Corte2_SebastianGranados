package com.dosw.bluevelvet.dto.reserva;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Datos de entrada para crear una reserva sobre una mesa.
 */
public record ReservaRequestDTO(

        @NotNull(message = "El id de la mesa es obligatorio")
        Long idMesa,

        @NotBlank(message = "El nombre del cliente es obligatorio")
        String cliente,

        @NotNull(message = "La fecha y hora de la reserva es obligatoria")
        @Future(message = "La fecha de la reserva debe ser futura")
        LocalDateTime fechaHora,

        @NotNull(message = "El numero de comensales es obligatorio")
        Integer comensales
) {
}
