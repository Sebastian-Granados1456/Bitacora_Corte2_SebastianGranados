package com.dosw.bluevelvet.dto.cuenta;

import jakarta.validation.constraints.NotNull;

/**
 * Datos de entrada para abrir una cuenta sobre una mesa.
 */
public record CuentaRequestDTO(

        @NotNull(message = "El id de la mesa es obligatorio")
        Long idMesa
) {
}
