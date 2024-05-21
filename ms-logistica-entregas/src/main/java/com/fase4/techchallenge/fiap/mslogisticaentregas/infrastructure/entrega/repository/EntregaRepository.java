package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Entrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega,Long> {

}
