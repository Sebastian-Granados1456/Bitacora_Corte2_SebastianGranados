package com.dosw.bluevelvet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosw.bluevelvet.controller.docs.AuthApi;
import com.dosw.bluevelvet.dto.auth.LoginRequestDTO;
import com.dosw.bluevelvet.dto.auth.RegistroRequestDTO;
import com.dosw.bluevelvet.dto.auth.TokenResponseDTO;
import com.dosw.bluevelvet.dto.auth.UsuarioResponseDTO;
import com.dosw.bluevelvet.mapper.usuario.UsuarioMapper;
import com.dosw.bluevelvet.model.domain.Usuario;
import com.dosw.bluevelvet.security.JwtUtil;
import com.dosw.bluevelvet.service.usuario.IUsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IUsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Override
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();
        String rol = user.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        log.info("Login exitoso: {} ({})", user.getUsername(), rol);

        return ResponseEntity.ok(TokenResponseDTO.builder()
                .token(jwtUtil.generateToken(user.getUsername(), rol))
                .tipo("Bearer")
                .rol(rol)
                .expiraEnMs(jwtUtil.getExpirationMs())
                .build());
    }

    @Override
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody @Valid RegistroRequestDTO dto) {
        Usuario creado = usuarioService.registrar(usuarioMapper.toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toResponse(creado));
    }
}
