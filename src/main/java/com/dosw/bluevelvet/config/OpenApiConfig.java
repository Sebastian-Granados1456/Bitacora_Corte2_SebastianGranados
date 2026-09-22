package com.dosw.bluevelvet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Personaliza la informacion general que se muestra en Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blueVelvetOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API del Restaurante Blue Velvet")
                        .description("API REST para gestionar mesas, reservas, pedidos, cuentas, "
                                + "el menu y el parqueadero del restaurante Blue Velvet")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Sebastian Granados")
                                .email("sebastian.granados@mail.escuelaing.edu.co")));
    }
}
