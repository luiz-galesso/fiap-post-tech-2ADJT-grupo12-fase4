package com.fase4.techchallenge.fiap.msgerenciamentoprodutos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsCatalogoProdutosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCatalogoProdutosApplication.class, args);
    }

}