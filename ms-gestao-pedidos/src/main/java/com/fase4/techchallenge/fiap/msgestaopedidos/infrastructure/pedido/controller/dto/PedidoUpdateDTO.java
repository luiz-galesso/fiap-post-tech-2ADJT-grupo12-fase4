package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Produto;

import java.util.List;

public record PedidoUpdateDTO(
        String emailCliente,
        Integer idEndereco,
        double valorPedido,
        String meioPagamento,
        List<Produto> produtos
) {
}
