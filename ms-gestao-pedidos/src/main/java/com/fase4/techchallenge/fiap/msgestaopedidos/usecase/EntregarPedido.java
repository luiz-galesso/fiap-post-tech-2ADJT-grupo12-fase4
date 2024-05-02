package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.enums.PedidoStatus;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EntregarPedido {
    private final PedidoGateway pedidoGateway;

    public Pedido execute(Long id) {

        Optional<Pedido> pedidoOptional = pedidoGateway.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new BussinessErrorException("Pedido Informado não Cadastrado.");
        }

        Pedido pedido = new Pedido(pedidoOptional.get().getIdPedido(),
                pedidoOptional.get().getCliente(),
                pedidoOptional.get().getProdutos(),
                pedidoOptional.get().getValorPedido(),
                PedidoStatus.ENTREGUE.toString(),
                pedidoOptional.get().getMeioPagamento(),
                pedidoOptional.get().getDataCriacao(),
                pedidoOptional.get().getDataPagamento(),
                LocalDateTime.now());

        return this.pedidoGateway.update(pedido);
    }
}
