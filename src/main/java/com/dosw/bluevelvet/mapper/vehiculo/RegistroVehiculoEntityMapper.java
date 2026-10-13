package com.dosw.bluevelvet.mapper.vehiculo;

import java.util.List;

import org.mapstruct.Mapper;

import com.dosw.bluevelvet.entity.RegistroVehiculoEntity;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

@Mapper(componentModel = "spring")
public interface RegistroVehiculoEntityMapper {

    RegistroVehiculo toDomain(RegistroVehiculoEntity entity);

    RegistroVehiculoEntity toEntity(RegistroVehiculo registro);

    List<RegistroVehiculo> toDomainList(List<RegistroVehiculoEntity> entities);
}
