package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class TabelaDeFreteInsertDTO {
    private Long cepOrigem;
    private Long cepDestinoInicial;
    private Long cepDestinoFinal;
    private double valorFrete;
    private Long prazoEntregaEmHoras;
}
