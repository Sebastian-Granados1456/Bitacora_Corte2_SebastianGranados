package com.dosw.bluevelvet.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token JWT que el cliente envia en el header Authorization.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {

    private String token;
    private String tipo;
    private String rol;
    private Long expiraEnMs;
}
