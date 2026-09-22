package com.dosw.bluevelvet.exception;

/**
 * Se lanza cuando se busca un recurso que no existe en el sistema.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
