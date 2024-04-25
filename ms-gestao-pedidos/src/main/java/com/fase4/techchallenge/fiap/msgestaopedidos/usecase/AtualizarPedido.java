package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.controller.dto.PedidoUpdateDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtualizarPedido {
    private final PedidoGateway pedidoGateway;

    public AtualizarPedido(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido execute(Long id, PedidoUpdateDTO pedidoUpdateDTO) {

        Optional<Pedido> pedidoOptional = pedidoGateway.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new BussinessErrorException("Pedido Informado não Cadastrado.");
        }

        Pedido pedido = new Pedido(pedidoOptional.get().getId(),
                pedidoUpdateDTO.emailCliente(),
                pedidoUpdateDTO.produtos(),
                pedidoUpdateDTO.endereco(),
                pedidoUpdateDTO.valorPedido(),
                pedidoOptional.get().getStatus(),
                pedidoUpdateDTO.meioPagamento(),
                pedidoOptional.get().getDataCriacao());

        return this.pedidoGateway.update(pedido);
    }
}
