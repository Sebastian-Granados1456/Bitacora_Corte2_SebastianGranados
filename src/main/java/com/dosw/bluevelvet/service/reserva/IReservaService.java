package com.dosw.bluevelvet.service.reserva;

import java.util.List;

import com.dosw.bluevelvet.dto.reserva.ReservaRequestDTO;
import com.dosw.bluevelvet.dto.reserva.ReservaResponseDTO;

public interface IReservaService {

    List<ReservaResponseDTO> obtenerTodas();

    ReservaResponseDTO buscarPorId(Long id);

    ReservaResponseDTO crear(ReservaRequestDTO dto);

    void cancelar(Long id);
}
