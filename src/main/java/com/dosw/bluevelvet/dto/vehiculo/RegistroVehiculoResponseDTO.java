package com.dosw.bluevelvet.dto.vehiculo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroVehiculoResponseDTO {

    private Long id;
    private String placa;
    private LocalDateTime entrada;
}
