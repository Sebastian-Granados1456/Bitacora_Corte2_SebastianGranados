package com.dosw.bluevelvet.validator.mesa;

import java.util.Collection;

import com.dosw.bluevelvet.model.domain.Mesa;

public interface IMesaValidator {

    void validarNumeroUnico(Integer numero, Collection<Mesa> mesasExistentes);
}
