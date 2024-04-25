package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RemoverPedidoPeloId {
    private final PedidoGateway pedidoGateway;

    public RemoverPedidoPeloId(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public boolean execute(Long id) {
        Optional<Pedido> pedidoOptional = pedidoGateway.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new BussinessErrorException("Pedido Informado não Cadastrado.");
        }
        pedidoGateway.remove(id);
        return true;
    }
}
