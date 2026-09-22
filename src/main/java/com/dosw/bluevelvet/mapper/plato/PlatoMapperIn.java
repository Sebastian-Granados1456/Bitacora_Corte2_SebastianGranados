package com.dosw.bluevelvet.mapper.plato;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.model.domain.Plato;

/**
 * Transforma el DTO de entrada en el objeto de dominio Plato.
 */
@Component
public class PlatoMapperIn {

    public Plato toDomain(PlatoRequestDTO dto) {
        Plato plato = new Plato();
        plato.setNombre(dto.nombre());
        plato.setPrecio(dto.precio());
        plato.setCategoria(dto.categoria());
        plato.setDisponible(true);
        return plato;
    }
}
