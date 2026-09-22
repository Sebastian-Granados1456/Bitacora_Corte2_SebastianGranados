package com.dosw.bluevelvet.service.cuenta;

import java.util.List;

import com.dosw.bluevelvet.model.domain.Cuenta;

public interface ICuentaService {

    List<Cuenta> obtenerTodas();

    Cuenta obtenerPorId(Long id);

    Cuenta abrir(Long idMesa);
}
