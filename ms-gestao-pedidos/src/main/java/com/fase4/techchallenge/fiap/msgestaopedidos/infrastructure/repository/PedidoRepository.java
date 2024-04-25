package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.repository;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
