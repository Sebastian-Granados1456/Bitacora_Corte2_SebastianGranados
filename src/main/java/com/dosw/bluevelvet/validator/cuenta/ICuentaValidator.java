package com.dosw.bluevelvet.validator.cuenta;

import java.util.Collection;

import com.dosw.bluevelvet.model.domain.Cuenta;

public interface ICuentaValidator {

    void validarSinCuentaAbierta(Long idMesa, Collection<Cuenta> cuentasExistentes);
}
