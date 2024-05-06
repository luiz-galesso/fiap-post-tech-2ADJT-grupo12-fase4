package com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClienteInsertDTO {

    private String email;
    private String nome;
    private LocalDate dataNascimento;
}
