package com.dosw.bluevelvet.dto.mesa;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Datos de entrada para crear una mesa.
 */
public record MesaRequestDTO(

        @NotNull(message = "El numero de mesa es obligatorio")
        @Min(value = 1, message = "El numero de mesa debe ser mayor a cero")
        Integer numero,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad debe ser al menos 1 comensal")
        Integer capacidad
) {
}
