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
        produtos.add(new Produto(1,1));

        return Pedido.builder()
                .status("GERADO")
                .meioPagamento("PIX")
                .valorPedido(200.00)
                .cliente(Cliente.builder()
                        .email("johnwick@gmail.com")
                        .nome("John Wick")
                        .endereco(new Endereco("Rua 25 de Março","25","Sé","AP25",2525252L,"São Paulo","SP","proximo a estação da Sé"))
                        .build())
                .produtos(produtos)
                .dataPagamento(null)
                .dataEntrega(null)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

}
