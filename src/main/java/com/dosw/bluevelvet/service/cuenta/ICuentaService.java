package com.dosw.bluevelvet.service.cuenta;

import java.util.List;

import com.dosw.bluevelvet.dto.cuenta.CuentaRequestDTO;
import com.dosw.bluevelvet.dto.cuenta.CuentaResponseDTO;

public interface ICuentaService {

    List<CuentaResponseDTO> obtenerTodas();

    CuentaResponseDTO buscarPorId(Long id);

    CuentaResponseDTO abrir(CuentaRequestDTO dto);
}
