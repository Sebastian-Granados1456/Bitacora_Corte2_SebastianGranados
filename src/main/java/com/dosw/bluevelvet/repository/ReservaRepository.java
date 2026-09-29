package com.dosw.bluevelvet.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    boolean existsByMesaIdAndFechaHora(Long idMesa, LocalDateTime fechaHora);
}
