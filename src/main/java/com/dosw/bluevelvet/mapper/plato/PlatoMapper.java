package com.dosw.bluevelvet.mapper.plato;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.model.domain.Plato;

/**
 * Traduce entre el DTO de entrada/salida y el objeto de dominio Plato.
 * Se inyecta en el Controller, nunca en el Service.
 */
@Mapper(componentModel = "spring")
public interface PlatoMapper {

    // RequestDTO -> Dominio: el id lo asigna el sistema, y todo plato nuevo
    // empieza disponible.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", constant = "true")
    Plato toDomain(PlatoRequestDTO dto);

    // Dominio -> ResponseDTO: todos los campos coinciden, MapStruct los
    // mapea sin anotaciones adicionales.
    PlatoResponseDTO toResponse(Plato plato);

    List<PlatoResponseDTO> toResponseList(List<Plato> platos);
}
