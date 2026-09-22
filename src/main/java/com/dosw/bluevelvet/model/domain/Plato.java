package com.dosw.bluevelvet.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {

    private Long id;
    private String nombre;
    private Double precio;
    private String categoria;
    private Boolean disponible;

    /**
     * Un plato es valido si tiene nombre, precio positivo y categoria definidos.
     */
    public Boolean esValido() {
        return nombre != null && !nombre.isBlank()
                && precio != null && precio > 0
                && categoria != null && !categoria.isBlank();
    }
}
