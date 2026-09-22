package com.dosw.bluevelvet.dto.cuenta;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de entrada para abrir una cuenta sobre una mesa.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequestDTO {

    @NotNull(message = "El id de la mesa es obligatorio")
    private Long idMesa;
}
