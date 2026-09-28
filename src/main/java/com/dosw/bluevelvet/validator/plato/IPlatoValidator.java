package com.dosw.bluevelvet.validator.plato;

/**
 * Contrato de las reglas de negocio del dominio Plato.
 */
public interface IPlatoValidator {

    void validarNombreUnico(String nombre);
}
