package com.dosw.bluevelvet.validator.usuario;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioValidator implements IUsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new RecursoDuplicadoException("Ya existe un usuario con ese email: " + email);
        }
    }
}
