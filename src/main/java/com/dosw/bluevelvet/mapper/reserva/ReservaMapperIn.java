package com.dosw.bluevelvet.mapper.reserva;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.reserva.ReservaRequestDTO;
import com.dosw.bluevelvet.model.domain.Reserva;

@Component
public class ReservaMapperIn {

    public Reserva toDomain(ReservaRequestDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setIdMesa(dto.idMesa());
        reserva.setCliente(dto.cliente());
        reserva.setFechaHora(dto.fechaHora());
        reserva.setComensales(dto.comensales());
        return reserva;
    }
}
