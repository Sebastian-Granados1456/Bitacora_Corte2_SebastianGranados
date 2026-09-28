package com.dosw.bluevelvet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.PlatoEntity;

/**
 * Acceso a la tabla "platos". JpaRepository ya provee save(), findById(),
 * findAll(), deleteById(), existsById(), count() sin escribir SQL.
 *
 * Los metodos declarados abajo siguen la convencion de nombres de Spring
 * Data: Spring genera la query automaticamente a partir del nombre del
 * metodo, sin necesidad de implementarlo.
 */
public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {
    boolean existsByNombreIgnoreCase(String nombre);

    List<PlatoEntity> findByDisponibleTrue();

    List<PlatoEntity> findByCategoriaIgnoreCase(String categoria);
}
