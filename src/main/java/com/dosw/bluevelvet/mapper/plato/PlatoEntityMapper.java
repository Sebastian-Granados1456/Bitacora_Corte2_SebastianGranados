package com.dosw.bluevelvet.mapper.plato;

import java.util.List;

import org.mapstruct.Mapper;

import com.dosw.bluevelvet.entity.PlatoEntity;
import com.dosw.bluevelvet.model.domain.Plato;

/**
 * Traduce entre el dominio Plato y su entidad de persistencia PlatoEntity.
 * El dominio no sabe que existe la base de datos; el Service es quien usa
 * este mapper antes/despues de llamar al Repository.
 */
@Mapper(componentModel = "spring")
public interface PlatoEntityMapper {

    Plato toDomain(PlatoEntity entity);

    PlatoEntity toEntity(Plato plato);

    List<Plato> toDomainList(List<PlatoEntity> entities);
}
