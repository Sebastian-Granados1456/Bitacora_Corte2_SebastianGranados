package com.dosw.bluevelvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosw.bluevelvet.entity.RegistroVehiculoEntity;

public interface RegistroVehiculoRepository extends JpaRepository<RegistroVehiculoEntity, Long> {

    boolean existsByPlacaIgnoreCaseAndSalidaIsNull(String placa);
}
