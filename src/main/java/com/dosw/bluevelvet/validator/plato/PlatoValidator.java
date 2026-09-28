package com.dosw.bluevelvet.validator.plato;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.repository.PlatoRepository;

import lombok.RequiredArgsConstructor;

/**
 * Regla de negocio propia de la carta de Blue Velvet. Ya no carga todos los
 * platos a memoria para buscar duplicados: le pregunta al Repository, que
 * resuelve la consulta con un indice en la columna "nombre".
 */
@Component
@RequiredArgsConstructor
public class PlatoValidator implements IPlatoValidator {

    private final PlatoRepository platoRepository;

    @Override
    public void validarNombreUnico(String nombre) {
        if (platoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new RecursoDuplicadoException("Ya existe un plato con ese nombre: " + nombre);
        }
    }
}
