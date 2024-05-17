package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EntregaDTO {
    private Long idPedido;
    private String emailCliente;
    private EnderecoDestinoDTO enderecoDestino;
    private LocalDateTime dataCriacao;

}
