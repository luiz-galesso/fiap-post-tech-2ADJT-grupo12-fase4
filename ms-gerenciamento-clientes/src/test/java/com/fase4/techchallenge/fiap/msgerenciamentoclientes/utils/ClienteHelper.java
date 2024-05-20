package com.fase4.techchallenge.fiap.msgerenciamentoclientes.utils;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClienteHelper {
    public static Cliente gerarCliente() {
        return Cliente.builder()
                .email("joao-silva@email.com")
                .nome("João Silva")
                .dataNascimento(LocalDate.of(1991, 06, 22))
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    public static ClienteInsertDTO gerarClienteInsert() {
        return new ClienteInsertDTO("joao-silva@email.com", "João Silva", LocalDate.of(1991, 06, 22));
    }

    public static ClienteUpdateDTO gerarClienteUpdate() {
        return new ClienteUpdateDTO("Joao dos Santos", LocalDate.of(1990, 9, 12));
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }
}
