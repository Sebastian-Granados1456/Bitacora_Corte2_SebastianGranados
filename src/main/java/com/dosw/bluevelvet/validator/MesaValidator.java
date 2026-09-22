package com.dosw.bluevelvet.validator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Mesa;

/**
 * Regla de negocio propia de las mesas de Blue Velvet.
 */
@Component
public class MesaValidator {

    /**
     * No pueden existir dos mesas con el mismo numero.
     */
    public void validarNumeroUnico(Map<Long, Mesa> mesas, Integer numero) {
        boolean numeroDuplicado = mesas.values().stream()
                .anyMatch(m -> m.getNumero().equals(numero));

        if (numeroDuplicado) {
            throw new RecursoDuplicadoException("Ya existe una mesa con el numero: " + numero);
        }
    }
}
