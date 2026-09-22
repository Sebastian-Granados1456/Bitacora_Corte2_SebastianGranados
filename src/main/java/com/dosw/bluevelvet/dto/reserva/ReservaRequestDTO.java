package com.dosw.bluevelvet.dto.reserva;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de entrada para crear una reserva sobre una mesa.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {

    @NotNull(message = "El id de la mesa es obligatorio")
    private Long idMesa;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String cliente;

    @NotNull(message = "La fecha y hora de la reserva es obligatoria")
    @Future(message = "La fecha de la reserva debe ser futura")
    private LocalDateTime fechaHora;

    @NotNull(message = "El numero de comensales es obligatorio")
    private Integer comensales;
}
