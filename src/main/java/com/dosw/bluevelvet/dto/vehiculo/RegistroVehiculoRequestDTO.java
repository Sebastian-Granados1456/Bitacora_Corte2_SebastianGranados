package com.dosw.bluevelvet.dto.vehiculo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de entrada para registrar el ingreso de un vehiculo al parqueadero.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroVehiculoRequestDTO {

    @NotBlank(message = "La placa es obligatoria")
    private String placa;
}
