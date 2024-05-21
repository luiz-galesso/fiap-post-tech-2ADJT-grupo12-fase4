package com.fase4.techchallenge.fiap.mslogisticaentregas.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
                .title("MS-LOGISTICA-ENTREGAS")
                .version("1.0.0")
                .description("Microserviço para logistica das entregas");

        return new OpenAPI().info(info);
    }
}
