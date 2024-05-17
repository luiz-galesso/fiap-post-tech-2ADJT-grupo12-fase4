package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

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

        Pedido pedido = pedidoGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Pedido Informado não Cadastrado."));

        pedido.setStatus(PedidoStatus.ENTREGUE.toString());
        pedido.setDataEntrega(LocalDateTime.now());

        return this.pedidoGateway.update(pedido);
    }
}
