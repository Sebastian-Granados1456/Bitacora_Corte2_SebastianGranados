package com.dosw.bluevelvet.dto.vehiculo;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos de entrada para registrar el ingreso de un vehiculo al parqueadero.
 */
public record RegistroVehiculoRequestDTO(

        @NotBlank(message = "La placa es obligatoria")
        String placa
) {
}
