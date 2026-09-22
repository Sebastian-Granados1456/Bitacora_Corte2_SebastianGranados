package com.dosw.bluevelvet.service.pedido;

import java.util.List;

import com.dosw.bluevelvet.dto.pedido.PedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;

public interface IPedidoService {

    List<PedidoResponseDTO> obtenerTodos();

    List<PedidoResponseDTO> obtenerPorMesa(Long idMesa);

    PedidoResponseDTO buscarPorId(Long id);

    PedidoResponseDTO crear(PedidoRequestDTO dto);
}
