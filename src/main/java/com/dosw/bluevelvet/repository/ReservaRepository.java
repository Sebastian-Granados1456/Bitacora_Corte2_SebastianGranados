package com.dosw.bluevelvet.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    // Usado por el Validator para verificar cruce de horario sin cargar
    // todas las reservas a memoria.
    boolean existsByMesaIdAndFechaHora(Long idMesa, LocalDateTime fechaHora);
}
