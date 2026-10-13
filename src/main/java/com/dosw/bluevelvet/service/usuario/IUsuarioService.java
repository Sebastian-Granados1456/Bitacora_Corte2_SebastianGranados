package com.dosw.bluevelvet.service.usuario;

import com.dosw.bluevelvet.model.domain.Usuario;

public interface IUsuarioService {

    Usuario registrar(Usuario usuario);

    Usuario obtenerPorEmail(String email);
}
