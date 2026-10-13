package com.dosw.bluevelvet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Personaliza la informacion general que se muestra en Swagger UI y agrega
 * el boton Authorize para enviar el token JWT.
 */
@Configuration
public class OpenApiConfig {

    private static final String BEARER = "bearerAuth";

    @Bean
    public OpenAPI blueVelvetOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API del Restaurante Blue Velvet")
                        .description("API REST para gestionar mesas, reservas, pedidos, cuentas, "
                                + "el menu y el parqueadero del restaurante Blue Velvet")
                        .version("v2.0")
                        .contact(new Contact()
                                .name("Sebastian Granados")
                                .email("sebastian.granados@mail.escuelaing.edu.co")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER))
                .components(new Components()
                        .addSecuritySchemes(BEARER, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
