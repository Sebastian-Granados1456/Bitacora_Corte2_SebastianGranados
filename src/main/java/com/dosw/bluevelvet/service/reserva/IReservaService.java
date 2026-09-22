package com.dosw.bluevelvet.service.reserva;

import java.util.List;

import com.dosw.bluevelvet.model.domain.Reserva;

public interface IReservaService {

    List<Reserva> obtenerTodas();

    Reserva obtenerPorId(Long id);

    Reserva crear(Reserva reserva);

    void cancelar(Long id);
}
