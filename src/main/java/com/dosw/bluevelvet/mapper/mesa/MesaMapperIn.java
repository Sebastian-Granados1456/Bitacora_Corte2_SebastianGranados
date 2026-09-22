package com.dosw.bluevelvet.mapper.mesa;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.mesa.MesaRequestDTO;
import com.dosw.bluevelvet.model.domain.EstadoMesa;
import com.dosw.bluevelvet.model.domain.Mesa;

@Component
public class MesaMapperIn {

    public Mesa toDomain(MesaRequestDTO dto) {
        Mesa mesa = new Mesa();
        mesa.setNumero(dto.numero());
        mesa.setCapacidad(dto.capacidad());
        mesa.setEstado(EstadoMesa.DISPONIBLE);
        mesa.setCuentaAbierta(false);
        return mesa;
    }
}
