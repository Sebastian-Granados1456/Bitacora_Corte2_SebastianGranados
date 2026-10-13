package com.dosw.bluevelvet.service.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.entity.UsuarioEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.usuario.UsuarioEntityMapper;
import com.dosw.bluevelvet.model.domain.Usuario;
import com.dosw.bluevelvet.repository.UsuarioRepository;
import com.dosw.bluevelvet.validator.usuario.IUsuarioValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioEntityMapper entityMapper;
    private final IUsuarioValidator validator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrar(Usuario usuario) {
        validator.validarEmailUnico(usuario.getEmail());

        usuario.setEmail(usuario.getEmail().toLowerCase());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        UsuarioEntity guardado = usuarioRepository.save(entityMapper.toEntity(usuario));

        log.info("Usuario registrado: id={}, rol={}", guardado.getId(), guardado.getRol());
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .map(entityMapper::toDomain)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + email));
    }
}
