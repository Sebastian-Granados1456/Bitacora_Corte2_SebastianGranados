package com.dosw.bluevelvet.service.mesa;

import java.util.List;

import com.dosw.bluevelvet.model.domain.Mesa;

public interface IMesaService {

    List<Mesa> obtenerTodas();

    Mesa obtenerPorId(Long id);

    Mesa crear(Mesa mesa);

    void marcarCuentaAbierta(Long id, boolean abierta);

    void eliminar(Long id);
}
