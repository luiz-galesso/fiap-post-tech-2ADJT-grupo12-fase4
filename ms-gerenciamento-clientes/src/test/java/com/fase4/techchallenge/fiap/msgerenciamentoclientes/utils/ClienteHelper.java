package com.fase4.techchallenge.fiap.msgerenciamentoclientes.utils;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

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

    private static final String USERNAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String[] EMAIL_PROVIDERS = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "email.com"};
    private static final Random random = new Random();
    public static String generateRandomEmail() {
        StringBuilder email = new StringBuilder();
        // Gerando o nome de usuário do e-mail
        int usernameLength = random.nextInt(10) + 5; // Nome de usuário com comprimento entre 5 e 14 caracteres
        for (int i = 0; i < usernameLength; i++) {
            char randomChar = USERNAME_CHARACTERS.charAt(random.nextInt(USERNAME_CHARACTERS.length()));
            email.append(randomChar);
        }
        // Adicionando o caractere '@' ao nome de usuário
        email.append("@");
        // Selecionando um provedor de e-mail aleatoriamente
        String randomProvider = EMAIL_PROVIDERS[random.nextInt(EMAIL_PROVIDERS.length)];
        email.append(randomProvider);

        return email.toString();
    }
    private String gerarEmailAleatorio() {
        String email = generateRandomEmail();
        return email;
    }
}
