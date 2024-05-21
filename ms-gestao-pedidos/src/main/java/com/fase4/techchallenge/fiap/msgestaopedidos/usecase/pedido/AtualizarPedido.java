package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

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

        Pedido pedido = pedidoGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Pedido Informado não Cadastrado."));

        if (pedidoUpdateDTO.meioPagamento() != pedido.getMeioPagamento() &&
                !pedido.getStatus().equalsIgnoreCase("GERADO")) {
            throw new BussinessErrorException("Não pode ser alterado o meio de Pagamento do Pedido. Status:" + pedido.getStatus());
        }

        pedido.setProdutos(pedidoUpdateDTO.produtos());
        pedido.setValorPedido(pedidoUpdateDTO.valorPedido());
        pedido.setValorFrete(pedidoUpdateDTO.valorFrete());
        pedido.setMeioPagamento(pedidoUpdateDTO.meioPagamento());

        return this.pedidoGateway.update(pedido);
    }

}
