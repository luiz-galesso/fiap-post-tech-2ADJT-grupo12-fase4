package com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.endereco.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnderecoUpdateDTO {
    private String logradouro;

    private String numero;

    private String bairro;

    private String complemento;

    private Long cep;

    private String cidade;

    private String estado;

    private String referencia;
}
