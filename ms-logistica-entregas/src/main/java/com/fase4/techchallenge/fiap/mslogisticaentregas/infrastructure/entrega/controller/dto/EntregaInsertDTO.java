package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EntregaInsertDTO {

    private Long idPedido;
    private String emailCliente;
    private EnderecoDestinoDTO enderecoDestino;
    private LocalDateTime dataCriacao;

}
