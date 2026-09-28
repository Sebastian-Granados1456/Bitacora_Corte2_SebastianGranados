package com.dosw.bluevelvet.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utilidad estatica para generar identificadores autoincrementales en
 * memoria mientras el sistema no cuenta con persistencia.
 */
public final class IdGenerator {

    private IdGenerator() {
    }

    public static Long siguiente(AtomicLong contador) {
        return contador.incrementAndGet();
    }
}
