package com.dosw.bluevelvet.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        Long idMesa,
        List<ItemPedidoResponseDTO> items,
        String estado,
        LocalDateTime timestamp,
        Double total
) {
}
