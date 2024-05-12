package com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.endereco.controller.dto;


import lombok.Data;

@Data
public class EnderecoInsertDTO {

    private String logradouro;

    private String numero;

    private String bairro;

    private String complemento;

    private Long cep;

    private String cidade;

    private String estado;

    private String referencia;
}
