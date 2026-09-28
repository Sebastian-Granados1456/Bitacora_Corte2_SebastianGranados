package com.dosw.bluevelvet.mapper.mesa;

import java.util.List;

import org.mapstruct.Mapper;

import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.model.domain.Mesa;

/**
 * Traduce entre el dominio Mesa y su entidad de persistencia MesaEntity.
 */
@Mapper(componentModel = "spring")
public interface MesaEntityMapper {

    Mesa toDomain(MesaEntity entity);

    MesaEntity toEntity(Mesa mesa);

    List<Mesa> toDomainList(List<MesaEntity> entities);
}
