package com.dosw.bluevelvet.service.plato;

import java.util.List;

import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;

public interface IPlatoService {

    List<PlatoResponseDTO> obtenerTodos();

    PlatoResponseDTO buscarPorId(Long id);

    PlatoResponseDTO crear(PlatoRequestDTO dto);

    void eliminar(Long id);
}
