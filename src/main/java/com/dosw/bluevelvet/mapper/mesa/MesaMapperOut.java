package com.dosw.bluevelvet.mapper.mesa;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;
import com.dosw.bluevelvet.model.domain.Mesa;

@Component
public class MesaMapperOut {

    public MesaResponseDTO toDTO(Mesa mesa) {
        return new MesaResponseDTO(
                mesa.getId(),
                mesa.getNumero(),
                mesa.getCapacidad(),
                mesa.getEstado().name(),
                mesa.getCuentaAbierta()
        );
    }
}
