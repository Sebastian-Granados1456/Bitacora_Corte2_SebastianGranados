package com.dosw.bluevelvet.service.vehiculo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoRequestDTO;
import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoResponseDTO;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.vehiculo.RegistroVehiculoMapperIn;
import com.dosw.bluevelvet.mapper.vehiculo.RegistroVehiculoMapperOut;
import com.dosw.bluevelvet.validator.RegistroVehiculoValidator;

class RegistroVehiculoServiceImplTest {

    private final RegistroVehiculoMapperIn mapperIn = new RegistroVehiculoMapperIn();
    private final RegistroVehiculoMapperOut mapperOut = new RegistroVehiculoMapperOut();
    private final RegistroVehiculoValidator validator = new RegistroVehiculoValidator();

    private RegistroVehiculoServiceImpl vehiculoService;
    private RegistroVehiculoRequestDTO requestDto;

    @BeforeEach
    void setUp() {
        vehiculoService = new RegistroVehiculoServiceImpl(mapperIn, mapperOut, validator);
        requestDto = new RegistroVehiculoRequestDTO("ABC123");
    }

    @Test
    @DisplayName("Registrar entrada valida debe retornar ResponseDTO")
    void registrarEntrada_placaValida_debeRetornarResponseDTO() {
        RegistroVehiculoResponseDTO resultado = vehiculoService.registrarEntrada(requestDto);

        assertNotNull(resultado.id());
        assertEquals("ABC123", resultado.placa());
    }

    @Test
    @DisplayName("Registrar entrada con placa ya activa debe lanzar RecursoDuplicadoException")
    void registrarEntrada_placaYaActiva_debeLanzarExcepcion() {
        vehiculoService.registrarEntrada(requestDto);

        assertThrows(RecursoDuplicadoException.class, () -> vehiculoService.registrarEntrada(requestDto));
    }

    @Test
    @DisplayName("Buscar registro inexistente debe lanzar RecursoNoEncontradoException")
    void buscarPorId_inexistente_debeLanzarExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> vehiculoService.buscarPorId(999L));
    }
}
