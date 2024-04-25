package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterPedidoPeloId {
    private final PedidoGateway pedidoGateway;

    public ObterPedidoPeloId(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido execute(Long id) {
        return this.pedidoGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido não localizado"));
    }
}
