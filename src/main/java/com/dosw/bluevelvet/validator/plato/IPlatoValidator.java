package com.dosw.bluevelvet.validator.plato;

import java.util.Collection;

import com.dosw.bluevelvet.model.domain.Plato;

/**
 * Contrato de las reglas de negocio del dominio Plato.
 */
public interface IPlatoValidator {

    void validarNombreUnico(String nombre, Collection<Plato> platosExistentes);
}
