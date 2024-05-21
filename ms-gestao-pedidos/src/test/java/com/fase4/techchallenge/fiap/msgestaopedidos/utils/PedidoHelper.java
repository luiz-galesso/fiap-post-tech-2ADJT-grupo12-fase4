package com.fase4.techchallenge.fiap.msgestaopedidos.utils;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Cliente;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Endereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Produto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoHelper {
    public static Pedido gerarPedido() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1,1,10.00));

        return Pedido.builder()
                .status("GERADO")
                .meioPagamento("PIX")
                .valorPedido(200.00)
                .emailCliente("john@email.com")
                .endereco(new Endereco(1, "Rua azul", "55", "Jardim verde", (String) null,  9360490L, "Monte Rosa", "SP", null))
                .produtos(produtos)
                .dataPagamento(null)
                .dataEntrega(null)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

}
