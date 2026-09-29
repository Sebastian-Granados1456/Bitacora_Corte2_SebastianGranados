package com.dosw.bluevelvet.mapper.pedido;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dosw.bluevelvet.entity.ItemPedidoEntity;
import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.entity.PedidoEntity;
import com.dosw.bluevelvet.model.domain.ItemPedido;
import com.dosw.bluevelvet.model.domain.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoEntityMapper {

    @Mapping(target = "idMesa", source = "mesa.id")
    Pedido toDomain(PedidoEntity entity);

    @Mapping(target = "mesa", source = "idMesa")
    @Mapping(target = "items", ignore = true)
    PedidoEntity toEntity(Pedido pedido);

    ItemPedido toDomain(ItemPedidoEntity entity);

    @Mapping(target = "pedido", ignore = true)
    ItemPedidoEntity toEntity(ItemPedido item);

    List<Pedido> toDomainList(List<PedidoEntity> entities);

    default MesaEntity map(Long idMesa) {
        if (idMesa == null) {
            return null;
        }
        MesaEntity mesa = new MesaEntity();
        mesa.setId(idMesa);
        return mesa;
    }
}
