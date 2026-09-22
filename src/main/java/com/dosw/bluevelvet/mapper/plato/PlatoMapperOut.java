package com.dosw.bluevelvet.mapper.plato;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.model.domain.Plato;

/**
 * Transforma el objeto de dominio Plato en el DTO de salida.
 */
@Component
public class PlatoMapperOut {

    public PlatoResponseDTO toDTO(Plato plato) {
        return new PlatoResponseDTO(
                plato.getId(),
                plato.getNombre(),
                plato.getPrecio(),
                plato.getCategoria(),
                plato.getDisponible()
        );
    }
}
