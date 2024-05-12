package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class TabelaDeFreteInsertDTO {
    private String cepOrigem;
    private String cepDestino;
    private double valorFrete;
    private Long prazoEntregaEmHoras;
}
