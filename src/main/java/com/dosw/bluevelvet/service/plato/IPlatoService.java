package com.dosw.bluevelvet.service.plato;

import java.util.List;

import com.dosw.bluevelvet.model.domain.Plato;

/**
 * Contrato del servicio de platos. Los metodos reciben y devuelven objetos
 * de DOMINIO, nunca DTOs — la traduccion la hace el Mapper en el Controller.
 */
public interface IPlatoService {

    List<Plato> obtenerTodos();

    Plato obtenerPorId(Long id);

    Plato crear(Plato plato);

    void eliminar(Long id);
}
