package com.fase4.techchallenge.fiap.msgerenciamentoclientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsGerenciamentoClientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsGerenciamentoClientesApplication.class, args);
    }

}