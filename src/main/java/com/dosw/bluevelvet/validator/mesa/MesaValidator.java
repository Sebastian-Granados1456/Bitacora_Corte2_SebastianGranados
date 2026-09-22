package com.dosw.bluevelvet.validator.mesa;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Mesa;

/**
 * Regla de negocio propia de las mesas de Blue Velvet.
 */
@Component
public class MesaValidator implements IMesaValidator {

    @Override
    public void validarNumeroUnico(Integer numero, Collection<Mesa> mesasExistentes) {
        boolean numeroDuplicado = mesasExistentes.stream()
                .anyMatch(m -> m.getNumero().equals(numero));

        if (numeroDuplicado) {
            throw new RecursoDuplicadoException("Ya existe una mesa con el numero: " + numero);
        }
    }
}
