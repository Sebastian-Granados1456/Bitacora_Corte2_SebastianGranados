package com.dosw.bluevelvet.mapper.reserva;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.reserva.ReservaResponseDTO;
import com.dosw.bluevelvet.model.domain.Reserva;

@Component
public class ReservaMapperOut {

    public ReservaResponseDTO toDTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getIdMesa(),
                reserva.getCliente(),
                reserva.getFechaHora(),
                reserva.getComensales()
        );
    }
}
