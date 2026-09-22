package com.dosw.bluevelvet.exception;

/**
 * Se lanza cuando se intenta crear un recurso que entra en conflicto con
 * uno ya existente (por ejemplo, un plato con el mismo nombre).
 */
public class RecursoDuplicadoException extends RuntimeException {

    public RecursoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
