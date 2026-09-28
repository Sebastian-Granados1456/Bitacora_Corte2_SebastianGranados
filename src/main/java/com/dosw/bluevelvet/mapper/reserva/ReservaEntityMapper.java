package com.dosw.bluevelvet.mapper.reserva;

import java.util.List;

import org.mapstruct.Mapping;

import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.entity.ReservaEntity;
import com.dosw.bluevelvet.model.domain.Reserva;

/**
 * Traduce entre el dominio Reserva (que solo conoce el id de la mesa) y su
 * entidad de persistencia ReservaEntity (que tiene la relacion @ManyToOne
 * completa hacia MesaEntity).
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface ReservaEntityMapper {

    @Mapping(target = "idMesa", source = "mesa.id")
    Reserva toDomain(ReservaEntity entity);

    @Mapping(target = "mesa", source = "idMesa")
    ReservaEntity toEntity(Reserva reserva);

    List<Reserva> toDomainList(List<ReservaEntity> entities);

    // MapStruct necesita este metodo auxiliar para construir un MesaEntity
    // "liviano" (solo con el id) a partir del Long idMesa del dominio.
    // No trae el resto de la mesa de la BD, solo referencia el id para
    // que Hibernate arme la foreign key correctamente al guardar.
    default MesaEntity map(Long idMesa) {
        if (idMesa == null) {
            return null;
        }
        MesaEntity mesa = new MesaEntity();
        mesa.setId(idMesa);
        return mesa;
    }
}
