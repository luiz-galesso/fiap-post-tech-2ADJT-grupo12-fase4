package com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClienteUpdateDTO {

    private String nome;
    private LocalDate dataNascimento;

}
