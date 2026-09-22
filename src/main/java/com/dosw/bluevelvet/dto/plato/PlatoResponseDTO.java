package com.dosw.bluevelvet.dto.plato;

/**
 * Datos de salida que representan un plato de la carta.
 */
public record PlatoResponseDTO(
        Long id,
        String nombre,
        Double precio,
        String categoria,
        Boolean disponible
) {
}
