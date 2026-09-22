package com.dosw.bluevelvet.validator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Plato;

/**
 * Regla de negocio propia de la carta de Blue Velvet.
 */
@Component
public class PlatoValidator {

    /**
     * No pueden existir dos platos con el mismo nombre en la carta.
     */
    public void validarNombreUnico(Map<Long, Plato> platos, String nombre) {
        boolean nombreDuplicado = platos.values().stream()
                .anyMatch(p -> p.getNombre().equalsIgnoreCase(nombre));

        if (nombreDuplicado) {
            throw new RecursoDuplicadoException("Ya existe un plato con ese nombre: " + nombre);
        }
    }
}
