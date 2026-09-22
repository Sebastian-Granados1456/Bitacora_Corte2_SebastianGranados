package com.dosw.bluevelvet.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dosw.bluevelvet.dto.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Intercepta las excepciones de todos los controllers y garantiza que el
 * cliente reciba siempre respuestas de error con el mismo formato.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // --- Excepciones de input (Bean Validation) -> 400 -----------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        log.warn("Validacion de input fallida: {}", errores);
        return ErrorResponseDTO.of(400, "Datos de entrada invalidos", errores, request.getRequestURI());
    }

    // --- Recurso no encontrado -> 404 -----------------------------------
    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleNoEncontrado(RecursoNoEncontradoException ex, HttpServletRequest request) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ErrorResponseDTO.of(404, "No encontrado", ex.getMessage(), request.getRequestURI());
    }

    // --- Conflicto de negocio -> 409 ------------------------------------
    @ExceptionHandler(RecursoDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleConflicto(RecursoDuplicadoException ex, HttpServletRequest request) {
        log.warn("Conflicto de negocio: {}", ex.getMessage());
        return ErrorResponseDTO.of(409, "Conflicto", ex.getMessage(), request.getRequestURI());
    }

    // --- Cualquier otro error no controlado -> 500 -----------------------
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("Error no controlado: {}", ex.getMessage(), ex);
        return ErrorResponseDTO.of(500, "Error interno", "Algo salio mal, intenta de nuevo", request.getRequestURI());
    }
}
