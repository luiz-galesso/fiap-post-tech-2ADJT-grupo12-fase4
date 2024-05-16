package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtemPedidosPeloCliente {
    private final PedidoGateway pedidoGateway;

    public List<Pedido> execute(String email) {
        return this.pedidoGateway.findByEmailCliente(email);
    }
}
