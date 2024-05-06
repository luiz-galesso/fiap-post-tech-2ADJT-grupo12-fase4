package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoUpdateDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarPedido {
    private final PedidoGateway pedidoGateway;

    public Pedido execute(Long id, PedidoUpdateDTO pedidoUpdateDTO) {

        Optional<Pedido> pedidoOptional = pedidoGateway.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new BussinessErrorException("Pedido Informado não Cadastrado.");
        }

        Pedido pedido = new Pedido(pedidoOptional.get().getIdPedido(),
                pedidoOptional.get().getCliente(),
                pedidoUpdateDTO.produtos(),
                pedidoUpdateDTO.valorPedido(),
                pedidoOptional.get().getStatus(),
                pedidoUpdateDTO.meioPagamento(),
                pedidoOptional.get().getDataCriacao(),
                null,
                null);

        return this.pedidoGateway.update(pedido);
    }
}
