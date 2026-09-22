package com.dosw.bluevelvet.mapper.pedido;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.pedido.ItemPedidoResponseDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;
import com.dosw.bluevelvet.model.domain.ItemPedido;
import com.dosw.bluevelvet.model.domain.Pedido;

/**
 * Transforma el objeto de dominio Pedido (y sus items) en DTOs de salida.
 */
@Component
public class PedidoMapperOut {

    public PedidoResponseDTO toDTO(Pedido pedido) {
        var itemsDto = pedido.getItems().stream()
                .map(this::toItemDTO)
                .toList();

        double total = pedido.getItems().stream()
                .mapToDouble(ItemPedido::subtotal)
                .sum();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getIdMesa(),
                itemsDto,
                pedido.getEstado().name(),
                pedido.getTimestamp(),
                total
        );
    }

    private ItemPedidoResponseDTO toItemDTO(ItemPedido item) {
        return new ItemPedidoResponseDTO(
                item.getIdPlato(),
                item.getNombrePlato(),
                item.getPrecioCongelado(),
                item.getCantidad(),
                item.subtotal()
        );
    }
}
