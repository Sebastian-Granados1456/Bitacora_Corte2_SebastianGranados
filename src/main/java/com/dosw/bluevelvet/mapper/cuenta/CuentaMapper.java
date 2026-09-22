package com.dosw.bluevelvet.mapper.cuenta;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dosw.bluevelvet.dto.cuenta.CuentaResponseDTO;
import com.dosw.bluevelvet.model.domain.Cuenta;

/**
 * Traduce el objeto de dominio Cuenta al DTO de salida. Cuenta no tiene
 * RequestDTO propio con campos suficientes para un mapeo automatico (el id
 * de mesa se pasa directo al Service), por lo que solo se expone toResponse.
 */
@Mapper(componentModel = "spring")
public interface CuentaMapper {

    @Mapping(target = "estado", expression = "java(cuenta.getEstado().name())")
    @Mapping(target = "total", expression = "java(cuenta.calcularTotal())")
    CuentaResponseDTO toResponse(Cuenta cuenta);

    List<CuentaResponseDTO> toResponseList(List<Cuenta> cuentas);
}
