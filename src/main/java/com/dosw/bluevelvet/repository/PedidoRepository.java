package com.dosw.bluevelvet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByMesaId(Long idMesa);
}
