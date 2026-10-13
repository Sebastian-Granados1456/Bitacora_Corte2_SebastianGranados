package com.dosw.bluevelvet.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Prueba las reglas de acceso con la aplicacion completa sobre H2.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.seed-password=Prueba2026")
class SecurityIntegrationTest {

    private static final String PLATO = "{\"nombre\":\"Bandeja %s\",\"precio\":28000,\"categoria\":\"PLATO_FUERTE\"}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String login(String email, String password) throws Exception {
        String body = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(body);
        return json.get("token").asText();
    }

    @Test
    @DisplayName("login - credenciales correctas devuelven 200 con token")
    void login_correcto_devuelveToken() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"chef@bluevelvet.com\",\"password\":\"Prueba2026\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.rol").value("CHEF"));
    }

    @Test
    @DisplayName("login - contraseña incorrecta devuelve 401")
    void login_passwordIncorrecto_devuelve401() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"chef@bluevelvet.com\",\"password\":\"incorrecta\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("sin token - endpoint protegido devuelve 401")
    void sinToken_devuelve401() throws Exception {
        mockMvc.perform(get("/api/v1/platos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("token invalido - devuelve 401")
    void tokenInvalido_devuelve401() throws Exception {
        mockMvc.perform(get("/api/v1/platos").header("Authorization", "Bearer tokenbasura"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("menu - es publico y devuelve 200 sin token")
    void menu_publico_devuelve200() throws Exception {
        mockMvc.perform(get("/api/v1/menu"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    @DisplayName("crear plato con rol CLIENTE devuelve 403")
    void crearPlato_rolCliente_devuelve403() throws Exception {
        mockMvc.perform(post("/api/v1/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PLATO.formatted("cliente")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("crear plato con token de CHEF devuelve 201")
    void crearPlato_tokenChef_devuelve201() throws Exception {
        String token = login("chef@bluevelvet.com", "Prueba2026");

        mockMvc.perform(post("/api/v1/platos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PLATO.formatted("chef")))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("eliminar plato con token de MESERO devuelve 403")
    void eliminarPlato_tokenMesero_devuelve403() throws Exception {
        String token = login("mesero@bluevelvet.com", "Prueba2026");

        mockMvc.perform(delete("/api/v1/platos/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("registro - crea un cliente con 201 y no devuelve el password")
    void registro_creaCliente() throws Exception {
        mockMvc.perform(post("/api/v1/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Ana\",\"email\":\"ana@mail.com\",\"password\":\"clave1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rol").value("CLIENTE"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @DisplayName("headers de seguridad presentes en las respuestas")
    void headersDeSeguridad() throws Exception {
        mockMvc.perform(get("/api/v1/menu"))
                .andExpect(header().string("X-Frame-Options", "DENY"))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().exists("Content-Security-Policy"));
    }
}
