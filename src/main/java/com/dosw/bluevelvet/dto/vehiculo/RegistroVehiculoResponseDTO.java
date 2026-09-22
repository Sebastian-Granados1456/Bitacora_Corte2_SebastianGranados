package com.dosw.bluevelvet.dto.vehiculo;

import java.time.LocalDateTime;

public record RegistroVehiculoResponseDTO(
        Long id,
        String placa,
        LocalDateTime entrada
) {
}
