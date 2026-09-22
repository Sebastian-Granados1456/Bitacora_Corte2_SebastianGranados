package com.dosw.bluevelvet.mapper.mesa;

import java.util.List;

import org.mapstruct.Mapping;

import com.dosw.bluevelvet.dto.mesa.MesaRequestDTO;
import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;
import com.dosw.bluevelvet.model.domain.Mesa;

/**
 * Traduce entre los DTOs de Mesa y el objeto de dominio. Se inyecta en el
 * Controller, nunca en el Service.
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface MesaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "DISPONIBLE")
    @Mapping(target = "cuentaAbierta", constant = "false")
    Mesa toDomain(MesaRequestDTO dto);

    @Mapping(target = "estado", expression = "java(mesa.getEstado().name())")
    MesaResponseDTO toResponse(Mesa mesa);

    List<MesaResponseDTO> toResponseList(List<Mesa> mesas);
}
