package com.dosw.bluevelvet.dto.cuenta;

import java.time.LocalDateTime;

public record CuentaResponseDTO(
        Long id,
        Long idMesa,
        Double total,
        String estado,
        LocalDateTime fechaApertura
) {
}
