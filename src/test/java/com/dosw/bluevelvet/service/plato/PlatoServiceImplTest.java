package com.dosw.bluevelvet.service.plato;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.plato.PlatoMapperIn;
import com.dosw.bluevelvet.mapper.plato.PlatoMapperOut;
import com.dosw.bluevelvet.validator.PlatoValidator;

class PlatoServiceImplTest {

    private final PlatoMapperIn mapperIn = new PlatoMapperIn();
    private final PlatoMapperOut mapperOut = new PlatoMapperOut();
    private final PlatoValidator validator = new PlatoValidator();

    private PlatoServiceImpl platoService;
    private PlatoRequestDTO requestDto;

    @BeforeEach
    void setUp() {
        platoService = new PlatoServiceImpl(mapperIn, mapperOut, validator);
        requestDto = new PlatoRequestDTO("Tacos", 15000.0, "PLATO_FUERTE");
    }

    @Test
    @DisplayName("Crear plato valido debe retornar ResponseDTO con id asignado")
    void crear_platoValido_debeRetornarResponseDTO() {
        PlatoResponseDTO resultado = platoService.crear(requestDto);

        assertNotNull(resultado.id());
        assertEquals("Tacos", resultado.nombre());
        assertTrue(resultado.disponible());
    }

    @Test
    @DisplayName("Crear plato con nombre duplicado debe lanzar RecursoDuplicadoException")
    void crear_nombreDuplicado_debeLanzarExcepcion() {
        platoService.crear(requestDto);

        PlatoRequestDTO duplicado = new PlatoRequestDTO("tacos", 18000.0, "PLATO_FUERTE");

        assertThrows(RecursoDuplicadoException.class, () -> platoService.crear(duplicado));
    }

    @Test
    @DisplayName("Buscar plato inexistente debe lanzar RecursoNoEncontradoException")
    void buscarPorId_platoInexistente_debeLanzarExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> platoService.buscarPorId(999L));
    }
}
