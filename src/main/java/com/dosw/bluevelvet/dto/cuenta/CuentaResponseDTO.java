package com.dosw.bluevelvet.dto.cuenta;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaResponseDTO {

    private Long id;
    private Long idMesa;
    private Double total;
    private String estado;
    private LocalDateTime fechaApertura;
}
