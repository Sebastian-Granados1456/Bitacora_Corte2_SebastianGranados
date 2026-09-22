package com.dosw.bluevelvet.dto.mesa;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de entrada para crear una mesa.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaRequestDTO {

    @NotNull(message = "El numero de mesa es obligatorio")
    @Min(value = 1, message = "El numero de mesa debe ser mayor a cero")
    private Integer numero;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1 comensal")
    private Integer capacidad;
}
