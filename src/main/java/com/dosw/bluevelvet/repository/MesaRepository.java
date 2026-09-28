package com.dosw.bluevelvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.MesaEntity;

public interface MesaRepository extends JpaRepository<MesaEntity, Long> {

    boolean existsByNumero(Integer numero);
}
