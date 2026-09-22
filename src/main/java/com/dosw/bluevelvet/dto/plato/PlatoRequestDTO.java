package com.dosw.bluevelvet.dto.plato;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de entrada para crear un plato de la carta.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatoRequestDTO {

    @NotBlank(message = "El nombre del plato es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private Double precio;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoria;
}
