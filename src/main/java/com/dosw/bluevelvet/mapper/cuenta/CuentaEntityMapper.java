package com.dosw.bluevelvet.mapper.cuenta;

import java.util.List;

import org.mapstruct.Mapping;

import com.dosw.bluevelvet.entity.CuentaEntity;
import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.model.domain.Cuenta;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CuentaEntityMapper {

    @Mapping(target = "idMesa", source = "mesa.id")
    @Mapping(target = "pedidos", ignore = true)
    Cuenta toDomain(CuentaEntity entity);

    @Mapping(target = "mesa", source = "idMesa")
    CuentaEntity toEntity(Cuenta cuenta);

    List<Cuenta> toDomainList(List<CuentaEntity> entities);

    default MesaEntity map(Long idMesa) {
        if (idMesa == null) {
            return null;
        }
        MesaEntity mesa = new MesaEntity();
        mesa.setId(idMesa);
        return mesa;
    }
}
