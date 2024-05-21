package com.fase4.techchallenge.fiap.msgestaopedidos.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
                .title("MS-GESTAO-PEDIDOS")
                .version("1.0.0")
                .description("Microserviço para gestão de pedidos");

        return new OpenAPI().info(info);
    }
}
