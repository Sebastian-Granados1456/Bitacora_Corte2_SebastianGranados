package com.dosw.bluevelvet.mapper.pedido;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.pedido.ItemPedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.model.domain.EstadoPedido;
import com.dosw.bluevelvet.model.domain.ItemPedido;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.service.plato.IPlatoService;

import lombok.RequiredArgsConstructor;

/**
 * Transforma el DTO de entrada en el objeto de dominio Pedido, congelando
 * el precio y el nombre de cada plato en el momento de la creacion.
 */
@Component
@RequiredArgsConstructor
public class PedidoMapperIn {

    private final IPlatoService platoService;

    public Pedido toDomain(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setIdMesa(dto.idMesa());
        pedido.setEstado(EstadoPedido.RECIBIDO);
        pedido.setTimestamp(java.time.LocalDateTime.now());
        pedido.setItems(dto.items().stream().map(this::toItem).toList());
        return pedido;
    }

    private ItemPedido toItem(ItemPedidoRequestDTO itemDto) {
        PlatoResponseDTO plato = platoService.buscarPorId(itemDto.idPlato());

        ItemPedido item = new ItemPedido();
        item.setIdPlato(plato.id());
        item.setNombrePlato(plato.nombre());
        item.setPrecioCongelado(plato.precio());
        item.setCantidad(itemDto.cantidad());
        return item;
    }
}
