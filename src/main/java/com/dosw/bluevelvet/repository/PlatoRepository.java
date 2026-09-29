package com.dosw.bluevelvet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.PlatoEntity;

public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {
    boolean existsByNombreIgnoreCase(String nombre);

    List<PlatoEntity> findByDisponibleTrue();

    List<PlatoEntity> findByCategoriaIgnoreCase(String categoria);
}
