package com.dosw.bluevelvet.dto.plato;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de salida que representan un plato de la carta.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoResponseDTO {

    private Long id;
    private String nombre;
    private Double precio;
    private String categoria;
    private Boolean disponible;
}
