package com.dosw.bluevelvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.CuentaEntity;
import com.dosw.bluevelvet.model.domain.EstadoCuenta;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {

    boolean existsByMesaIdAndEstado(Long idMesa, EstadoCuenta estado);
}
