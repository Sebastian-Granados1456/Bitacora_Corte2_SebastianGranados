package com.dosw.bluevelvet.validator.vehiculo;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.repository.RegistroVehiculoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegistroVehiculoValidator implements IRegistroVehiculoValidator {

    private final RegistroVehiculoRepository registroVehiculoRepository;

    @Override
    public void validarSinRegistroActivo(String placa) {
        if (registroVehiculoRepository.existsByPlacaIgnoreCaseAndSalidaIsNull(placa)) {
            throw new RecursoDuplicadoException("El vehiculo con placa " + placa + " ya tiene un registro activo");
        }
    }
}
