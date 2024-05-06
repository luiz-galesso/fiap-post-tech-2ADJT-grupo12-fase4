package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntregadorInsertDTO {
    private String nome;
    private Long cnpj;
    private String situacao;
    private Long quantidadeRecursosDisponiveis;
}
