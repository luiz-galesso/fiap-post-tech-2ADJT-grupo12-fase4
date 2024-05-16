package com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.repository.PedidoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PedidoGateway {

    private PedidoRepository pedidoRepository;

    public PedidoGateway(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido create(Pedido pedido) {
        return this.pedidoRepository.save(pedido);
    }

    public Pedido update(Pedido pedido) {
        return this.pedidoRepository.save(pedido);
    }

    public Optional<Pedido> findById(Long id) {
        return this.pedidoRepository.findById(id);
    }

    public void remove(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> findByEmailCliente(String email) {
        return this.pedidoRepository.findByEmailCliente(email);
    }
}
