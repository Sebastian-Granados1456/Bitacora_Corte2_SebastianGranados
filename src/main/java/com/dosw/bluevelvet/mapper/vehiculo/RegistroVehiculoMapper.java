package com.dosw.bluevelvet.mapper.vehiculo;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoRequestDTO;
import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoResponseDTO;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

@Mapper(componentModel = "spring")
public interface RegistroVehiculoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "placa", expression = "java(dto.getPlaca().toUpperCase())")
    @Mapping(target = "entrada", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "salida", ignore = true)
    RegistroVehiculo toDomain(RegistroVehiculoRequestDTO dto);

    RegistroVehiculoResponseDTO toResponse(RegistroVehiculo registro);

    List<RegistroVehiculoResponseDTO> toResponseList(List<RegistroVehiculo> registros);
}
