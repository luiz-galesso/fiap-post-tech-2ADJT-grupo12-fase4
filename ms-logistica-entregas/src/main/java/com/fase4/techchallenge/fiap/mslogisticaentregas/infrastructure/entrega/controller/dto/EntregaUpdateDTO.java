package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntregaUpdateDTO {
    private String nome;
    private Long cnpj;
    private String situacao;
}
