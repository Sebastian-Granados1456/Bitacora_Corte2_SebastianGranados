package com.dosw.bluevelvet.controller.docs;

import org.springframework.http.ResponseEntity;

import com.dosw.bluevelvet.dto.auth.LoginRequestDTO;
import com.dosw.bluevelvet.dto.auth.RegistroRequestDTO;
import com.dosw.bluevelvet.dto.auth.TokenResponseDTO;
import com.dosw.bluevelvet.dto.auth.UsuarioResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticacion", description = "Login con JWT y registro de clientes")
@SecurityRequirements
public interface AuthApi {

    @Operation(summary = "Iniciar sesion", description = "Devuelve un token JWT para usar en el boton Authorize")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login correcto, devuelve el token"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "401", description = "Email o contraseña incorrectos")
    })
    ResponseEntity<TokenResponseDTO> login(LoginRequestDTO dto);

    @Operation(summary = "Registrar cliente", description = "Crea una cuenta con rol CLIENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente registrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un usuario con ese email")
    })
    ResponseEntity<UsuarioResponseDTO> registrar(RegistroRequestDTO dto);
}
