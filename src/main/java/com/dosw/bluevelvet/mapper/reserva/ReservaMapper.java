package com.dosw.bluevelvet.mapper.reserva;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dosw.bluevelvet.dto.reserva.ReservaRequestDTO;
import com.dosw.bluevelvet.dto.reserva.ReservaResponseDTO;
import com.dosw.bluevelvet.model.domain.Reserva;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(target = "id", ignore = true)
    Reserva toDomain(ReservaRequestDTO dto);

    ReservaResponseDTO toResponse(Reserva reserva);

    List<ReservaResponseDTO> toResponseList(List<Reserva> reservas);
}
