package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.controller.dto;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Endereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Produto;

import java.util.List;

public record PedidoInsertDTO(
        String emailCliente,
        List<Produto> produtos,
        Endereco endereco,
        double valorPedido,
        String meioPagamento
) {
}
