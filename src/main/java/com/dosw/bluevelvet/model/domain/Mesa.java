package com.dosw.bluevelvet.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa {

    private Long id;
    private Integer numero;
    private Integer capacidad;
    private EstadoMesa estado;
    private Boolean cuentaAbierta;

    /**
     * Una mesa esta disponible si su estado es DISPONIBLE y no tiene
     * una cuenta abierta actualmente.
     */
    public boolean estaDisponible() {
        return estado == EstadoMesa.DISPONIBLE && Boolean.FALSE.equals(cuentaAbierta);
    }

    public void abrirCuenta() {
        this.cuentaAbierta = true;
    }

    public void cerrarCuenta() {
        this.cuentaAbierta = false;
    }
}
