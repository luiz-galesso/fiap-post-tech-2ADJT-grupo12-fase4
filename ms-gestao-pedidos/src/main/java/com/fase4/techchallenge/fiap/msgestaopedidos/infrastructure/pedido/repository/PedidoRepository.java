package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.repository;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEmailCliente(String email);
}
