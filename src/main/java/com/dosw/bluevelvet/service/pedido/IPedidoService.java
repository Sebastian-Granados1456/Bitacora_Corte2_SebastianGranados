package com.dosw.bluevelvet.service.pedido;

import java.util.List;

import com.dosw.bluevelvet.model.domain.Pedido;

public interface IPedidoService {

    List<Pedido> obtenerTodos();

    List<Pedido> obtenerPorMesa(Long idMesa);

    Pedido obtenerPorId(Long id);

    Pedido crear(Pedido pedido);
}
