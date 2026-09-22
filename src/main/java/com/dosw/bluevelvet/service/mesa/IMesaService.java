package com.dosw.bluevelvet.service.mesa;

import java.util.List;

import com.dosw.bluevelvet.dto.mesa.MesaRequestDTO;
import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;

public interface IMesaService {

    List<MesaResponseDTO> obtenerTodas();

    MesaResponseDTO buscarPorId(Long id);

    MesaResponseDTO crear(MesaRequestDTO dto);

    void marcarCuentaAbierta(Long id, boolean abierta);

    void eliminar(Long id);
}
