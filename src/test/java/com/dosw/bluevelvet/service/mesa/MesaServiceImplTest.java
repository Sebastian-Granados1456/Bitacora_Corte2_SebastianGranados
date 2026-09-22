package com.dosw.bluevelvet.service.mesa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dosw.bluevelvet.dto.mesa.MesaRequestDTO;
import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.mesa.MesaMapperIn;
import com.dosw.bluevelvet.mapper.mesa.MesaMapperOut;
import com.dosw.bluevelvet.validator.MesaValidator;

class MesaServiceImplTest {

    private final MesaMapperIn mapperIn = new MesaMapperIn();
    private final MesaMapperOut mapperOut = new MesaMapperOut();
    private final MesaValidator validator = new MesaValidator();

    private MesaServiceImpl mesaService;
    private MesaRequestDTO requestDto;

    @BeforeEach
    void setUp() {
        mesaService = new MesaServiceImpl(mapperIn, mapperOut, validator);
        requestDto = new MesaRequestDTO(5, 4);
    }

    @Test
    @DisplayName("Crear mesa valida debe retornar ResponseDTO en estado DISPONIBLE")
    void crear_mesaValida_debeRetornarResponseDTO() {
        MesaResponseDTO resultado = mesaService.crear(requestDto);

        assertNotNull(resultado.id());
        assertEquals("DISPONIBLE", resultado.estado());
    }

    @Test
    @DisplayName("Crear mesa con numero duplicado debe lanzar RecursoDuplicadoException")
    void crear_numeroDuplicado_debeLanzarExcepcion() {
        mesaService.crear(requestDto);

        assertThrows(RecursoDuplicadoException.class, () -> mesaService.crear(new MesaRequestDTO(5, 2)));
    }

    @Test
    @DisplayName("Buscar mesa inexistente debe lanzar RecursoNoEncontradoException")
    void buscarPorId_inexistente_debeLanzarExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> mesaService.buscarPorId(1L));
    }
}
