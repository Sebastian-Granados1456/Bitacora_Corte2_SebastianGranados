package com.dosw.bluevelvet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
