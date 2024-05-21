package com.fase4.techchallenge.fiap.mslogisticaentregas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsLogisticaEntregasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsLogisticaEntregasApplication.class, args);
    }

}