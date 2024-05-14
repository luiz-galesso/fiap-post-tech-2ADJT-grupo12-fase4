package com.fase4.techchallenge.fiap.msgestaopedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsGestaoPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGestaoPedidosApplication.class, args);
	}

}
