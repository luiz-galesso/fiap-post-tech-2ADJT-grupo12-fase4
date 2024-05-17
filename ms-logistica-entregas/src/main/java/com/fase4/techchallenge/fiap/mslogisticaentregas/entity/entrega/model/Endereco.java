package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;

    private String numero;

    private String bairro;

    private String complemento;

    private Long cep;

    private String cidade;

    private String estado;

    private String referencia;

}

