package com.dosw.bluevelvet.mapper.plato;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.model.domain.Plato;

@Mapper(componentModel = "spring")
public interface PlatoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", constant = "true")
    Plato toDomain(PlatoRequestDTO dto);

    PlatoResponseDTO toResponse(Plato plato);

    List<PlatoResponseDTO> toResponseList(List<Plato> platos);
}
