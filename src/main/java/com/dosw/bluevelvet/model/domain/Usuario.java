package com.dosw.bluevelvet.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private Rol rol;

    public boolean esAdmin() {
        return rol == Rol.ADMIN;
    }
}
