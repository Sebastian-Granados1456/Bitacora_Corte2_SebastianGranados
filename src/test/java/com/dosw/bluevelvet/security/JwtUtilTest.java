package com.dosw.bluevelvet.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

    private static final String SECRET = "dGVzdC1zZWNyZXQtcGFyYS1sYXMtcHJ1ZWJhcy1kZWwtand0LWJsdWV2ZWx2ZXQ=";

    private final JwtUtil jwtUtil = new JwtUtil(SECRET, 60_000);

    @Test
    @DisplayName("generateToken - el token es valido y guarda email y rol")
    void generateToken_tokenValidoConClaims() {
        String token = jwtUtil.generateToken("chef@bluevelvet.com", "CHEF");

        assertTrue(jwtUtil.isValid(token));
        assertEquals("chef@bluevelvet.com", jwtUtil.extractClaims(token).getSubject());
        assertEquals("CHEF", jwtUtil.extractClaims(token).get("rol"));
    }

    @Test
    @DisplayName("isValid - un token alterado no es valido")
    void isValid_tokenAlterado_false() {
        String token = jwtUtil.generateToken("chef@bluevelvet.com", "CHEF");

        assertFalse(jwtUtil.isValid(token + "x"));
        assertFalse(jwtUtil.isValid("tokenbasura"));
    }

    @Test
    @DisplayName("isValid - un token expirado no es valido")
    void isValid_tokenExpirado_false() {
        JwtUtil expirado = new JwtUtil(SECRET, -1000);
        String token = expirado.generateToken("chef@bluevelvet.com", "CHEF");

        assertFalse(jwtUtil.isValid(token));
    }

    @Test
    @DisplayName("constructor - sin secret falla al arrancar")
    void constructor_sinSecret_lanzaExcepcion() {
        assertThrows(IllegalStateException.class, () -> new JwtUtil("", 1000));
    }
}
