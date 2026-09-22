package com.dosw.bluevelvet.dto.mesa;

/**
 * Datos de salida que representan una mesa del restaurante.
 */
public record MesaResponseDTO(
        Long id,
        Integer numero,
        Integer capacidad,
        String estado,
        Boolean cuentaAbierta
) {
}
