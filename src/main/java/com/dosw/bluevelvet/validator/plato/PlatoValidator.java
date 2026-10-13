package com.dosw.bluevelvet.validator.plato;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.repository.PlatoRepository;

import lombok.RequiredArgsConstructor;

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
