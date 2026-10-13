package com.dosw.bluevelvet.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "registrgio_vehiculo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroVehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa", nullable = false, length = 10)
    private String placa;

    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Column(name = "salida")
    private LocalDateTime salida;
}
