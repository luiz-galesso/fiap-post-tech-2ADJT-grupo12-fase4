package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Origem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "origem_generator")
    @SequenceGenerator(name = "origem_generator", sequenceName = "origem_sequence", allocationSize = 1)
    private Long id;

    private String logradouro;

    private String numero;

    private String bairro;

    private String complemento;

    private Long cep;

    private String cidade;

    private String estado;

    private String referencia;

}

