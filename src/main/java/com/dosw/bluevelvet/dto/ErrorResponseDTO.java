package com.dosw.bluevelvet.dto;

import java.time.LocalDateTime;

/**
 * Formato estandar de respuesta para todos los errores de la API.
 */
public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {

    public static ErrorResponseDTO of(int status, String error, String message, String path) {
        return new ErrorResponseDTO(LocalDateTime.now(), status, error, message, path);
    }
}
