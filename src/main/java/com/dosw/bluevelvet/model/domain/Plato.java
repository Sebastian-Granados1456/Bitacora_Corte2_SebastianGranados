package com.dosw.bluevelvet.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plato {

    private Long id;
    private String nombre;
    private Double precio;
    private String categoria;
    private Boolean disponible;

    // Comportamiento de negocio propio del objeto: el dominio sabe que puede
    // hacer, sin depender de estado externo. Las validaciones de formato
    // (nombre no vacio, precio positivo) ya las garantiza el RequestDTO.
    public boolean estaDisponible() {
        return Boolean.TRUE.equals(disponible);
    }

    public void activar() {
        this.disponible = true;
    }

    public void desactivar() {
        this.disponible = false;
    }
}
