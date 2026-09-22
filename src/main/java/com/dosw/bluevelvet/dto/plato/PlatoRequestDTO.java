package com.dosw.bluevelvet.dto.plato;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Datos de entrada para crear un plato de la carta.
 */
public record PlatoRequestDTO(

        @NotBlank(message = "El nombre del plato es obligatorio")
        String nombre,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
        Double precio,

        @NotBlank(message = "La categoria es obligatoria")
        String categoria
) {
}
