package com.dosw.bluevelvet.dto.pedido;

public record ItemPedidoResponseDTO(
        Long idPlato,
        String nombrePlato,
        Double precioCongelado,
        Integer cantidad,
        Double subtotal
) {
}
