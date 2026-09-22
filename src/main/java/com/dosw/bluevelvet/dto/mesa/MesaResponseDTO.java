package com.dosw.bluevelvet.dto.mesa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de salida que representan una mesa del restaurante.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaResponseDTO {

    private Long id;
    private Integer numero;
    private Integer capacidad;
    private String estado;
    private Boolean cuentaAbierta;
}
