package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObterPedidoPeloId {
    private final PedidoGateway pedidoGateway;

    public Pedido execute(Long id) {
        return this.pedidoGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido não localizado"));
    }
}
