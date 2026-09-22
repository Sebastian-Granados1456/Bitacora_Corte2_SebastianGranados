package com.dosw.bluevelvet.mapper.pedido;

import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.dosw.bluevelvet.dto.pedido.ItemPedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.ItemPedidoResponseDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;
import com.dosw.bluevelvet.model.domain.ItemPedido;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.model.domain.Plato;
import com.dosw.bluevelvet.service.plato.IPlatoService;

/**
 * Traduce entre los DTOs de Pedido y el objeto de dominio. Congela precio y
 * nombre del plato al momento de crear cada item, consultando IPlatoService
 * (un Service puede depender de otro Service via interfaz).
 */
@Mapper(componentModel = "spring")
public abstract class PedidoMapper {

    @Autowired
    protected IPlatoService platoService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "RECIBIDO")
    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "items", source = "items")
    public abstract Pedido toDomain(PedidoRequestDTO dto);

    protected ItemPedido toItem(ItemPedidoRequestDTO itemDto) {
        Plato plato = platoService.obtenerPorId(itemDto.getIdPlato());
        return ItemPedido.builder()
                .idPlato(plato.getId())
                .nombrePlato(plato.getNombre())
                .precioCongelado(plato.getPrecio())
                .cantidad(itemDto.getCantidad())
                .build();
    }

    protected List<ItemPedido> toItems(List<ItemPedidoRequestDTO> items) {
        return items.stream().map(this::toItem).toList();
    }

    @Mapping(target = "estado", expression = "java(pedido.getEstado().name())")
    @Mapping(target = "total", expression = "java(pedido.calcularTotal())")
    public abstract PedidoResponseDTO toResponse(Pedido pedido);

    @Mapping(target = "subtotal", expression = "java(item.subtotal())")
    public abstract ItemPedidoResponseDTO toItemResponse(ItemPedido item);

    public abstract List<PedidoResponseDTO> toResponseList(List<Pedido> pedidos);
}
