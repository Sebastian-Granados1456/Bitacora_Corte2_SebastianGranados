package com.dosw.bluevelvet.mapper.reserva;

import java.util.List;

import org.mapstruct.Mapping;

import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.entity.ReservaEntity;
import com.dosw.bluevelvet.model.domain.Reserva;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ReservaEntityMapper {

    @Mapping(target = "idMesa", source = "mesa.id")
    Reserva toDomain(ReservaEntity entity);

    @Mapping(target = "mesa", source = "idMesa")
    ReservaEntity toEntity(Reserva reserva);

    List<Reserva> toDomainList(List<ReservaEntity> entities);

    default MesaEntity map(Long idMesa) {
        if (idMesa == null) {
            return null;
        }
        MesaEntity mesa = new MesaEntity();
        mesa.setId(idMesa);
        return mesa;
    }
}
