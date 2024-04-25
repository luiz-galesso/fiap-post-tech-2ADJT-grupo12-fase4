package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.enums.PedidoStatus;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.controller.dto.PedidoInsertDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CadastrarPedido {
    private final PedidoGateway pedidoGateway;

    public CadastrarPedido(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido execute(PedidoInsertDTO pedidoInsertDTO) {

        Pedido pedido = new Pedido(pedidoInsertDTO.emailCliente(),
                pedidoInsertDTO.produtos(),
                pedidoInsertDTO.endereco(),
                pedidoInsertDTO.valorPedido(),
                PedidoStatus.GERADO.toString(),
                pedidoInsertDTO.meioPagamento(),
                LocalDateTime.now());

        return this.pedidoGateway.create(pedido);
    }
}
