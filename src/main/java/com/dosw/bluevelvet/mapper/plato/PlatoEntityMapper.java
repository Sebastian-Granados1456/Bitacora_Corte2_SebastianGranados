package com.dosw.bluevelvet.mapper.plato;

import java.util.List;

import org.mapstruct.Mapper;

import com.dosw.bluevelvet.entity.PlatoEntity;
import com.dosw.bluevelvet.model.domain.Plato;

@Mapper(componentModel = "spring")
public interface PlatoEntityMapper {

    Plato toDomain(PlatoEntity entity);

    PlatoEntity toEntity(Plato plato);

    List<Plato> toDomainList(List<PlatoEntity> entities);
}
