package com.dosw.bluevelvet.service.vehiculo;

import java.util.List;

import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoRequestDTO;
import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoResponseDTO;

public interface IRegistroVehiculoService {

    List<RegistroVehiculoResponseDTO> obtenerTodos();

    RegistroVehiculoResponseDTO buscarPorId(Long id);

    RegistroVehiculoResponseDTO registrarEntrada(RegistroVehiculoRequestDTO dto);
}
