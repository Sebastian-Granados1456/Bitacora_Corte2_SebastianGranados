package com.dosw.bluevelvet.service.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dosw.bluevelvet.entity.UsuarioEntity;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.usuario.UsuarioEntityMapper;
import com.dosw.bluevelvet.model.domain.Rol;
import com.dosw.bluevelvet.model.domain.Usuario;
import com.dosw.bluevelvet.repository.UsuarioRepository;
import com.dosw.bluevelvet.validator.usuario.IUsuarioValidator;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioEntityMapper entityMapper;

    @Mock
    private IUsuarioValidator validator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl service;

    @Test
    @DisplayName("registrar - guarda el password hasheado, nunca en texto plano")
    void registrar_hasheaPassword() {
        Usuario entrada = Usuario.builder().nombre("Ana").email("Ana@Mail.com").password("clave1234").rol(Rol.CLIENTE).build();
        UsuarioEntity guardado = new UsuarioEntity(1L, "Ana", "ana@mail.com", "$2a$10$hash", Rol.CLIENTE);

        when(passwordEncoder.encode("clave1234")).thenReturn("$2a$10$hash");
        when(entityMapper.toEntity(any())).thenReturn(guardado);
        when(usuarioRepository.save(guardado)).thenReturn(guardado);
        when(entityMapper.toDomain(guardado)).thenReturn(Usuario.builder().id(1L).email("ana@mail.com").rol(Rol.CLIENTE).build());

        Usuario resultado = service.registrar(entrada);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(entityMapper).toEntity(captor.capture());
        assertEquals("$2a$10$hash", captor.getValue().getPassword());
        assertEquals("ana@mail.com", captor.getValue().getEmail());
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("registrar - email repetido lanza RecursoDuplicadoException y no guarda")
    void registrar_emailDuplicado_lanzaExcepcion() {
        Usuario entrada = Usuario.builder().email("ana@mail.com").password("clave1234").build();
        doThrow(new RecursoDuplicadoException("duplicado")).when(validator).validarEmailUnico("ana@mail.com");

        assertThrows(RecursoDuplicadoException.class, () -> service.registrar(entrada));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("obtenerPorEmail - email inexistente lanza RecursoNoEncontradoException")
    void obtenerPorEmail_inexistente_lanzaExcepcion() {
        when(usuarioRepository.findByEmailIgnoreCase("nadie@mail.com")).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorEmail("nadie@mail.com"));
    }
}
