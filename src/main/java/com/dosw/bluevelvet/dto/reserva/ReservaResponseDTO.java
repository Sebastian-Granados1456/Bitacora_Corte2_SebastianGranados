package com.dosw.bluevelvet.dto.reserva;

import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        Long idMesa,
        String cliente,
        LocalDateTime fechaHora,
        Integer comensales
) {
}
