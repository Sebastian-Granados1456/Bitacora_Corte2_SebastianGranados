package com.dosw.bluevelvet.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.entity.UsuarioEntity;
import com.dosw.bluevelvet.model.domain.Rol;
import com.dosw.bluevelvet.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Crea un usuario por rol para poder probar la API. La contraseña viene de
 * la variable SEED_PASSWORD; si no existe, no se crea ningun usuario.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed-password:}")
    private String seedPassword;

    @Override
    public void run(String... args) {
        if (seedPassword == null || seedPassword.isBlank()) {
            log.info("SEED_PASSWORD no definido: no se crean usuarios de prueba");
            return;
        }

        List.of(Rol.values()).stream()
                .filter(rol -> !usuarioRepository.existsByEmailIgnoreCase(emailDe(rol)))
                .forEach(rol -> {
                    usuarioRepository.save(new UsuarioEntity(null, "Usuario " + rol.name().toLowerCase(),
                            emailDe(rol), passwordEncoder.encode(seedPassword), rol));
                    log.info("Usuario de prueba creado: {}", emailDe(rol));
                });
    }

    private String emailDe(Rol rol) {
        return rol.name().toLowerCase() + "@bluevelvet.com";
    }
}
