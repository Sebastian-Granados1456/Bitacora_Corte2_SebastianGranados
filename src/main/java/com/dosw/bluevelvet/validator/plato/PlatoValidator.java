package com.dosw.bluevelvet.validator.plato;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Plato;

/**
 * Regla de negocio propia de la carta de Blue Velvet. Sin dependencias de
 * Spring MVC ni de otras capas: solo recibe parametros y valida.
 */
@Component
public class PlatoValidator implements IPlatoValidator {

    @Override
    public void validarNombreUnico(String nombre, Collection<Plato> platosExistentes) {
        boolean nombreDuplicado = platosExistentes.stream()
                .anyMatch(p -> p.getNombre().equalsIgnoreCase(nombre));

        if (nombreDuplicado) {
            throw new RecursoDuplicadoException("Ya existe un plato con ese nombre: " + nombre);
        }
    }
}
