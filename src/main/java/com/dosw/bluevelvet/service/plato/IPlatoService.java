package com.dosw.bluevelvet.service.plato;

import java.util.List;

import com.dosw.bluevelvet.model.domain.Plato;

public interface IPlatoService {

    List<Plato> obtenerTodos();

    Plato obtenerPorId(Long id);

    Plato crear(Plato plato);

    void eliminar(Long id);
}
