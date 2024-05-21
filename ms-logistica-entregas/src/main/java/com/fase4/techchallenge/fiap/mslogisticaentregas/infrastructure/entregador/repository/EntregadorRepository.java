package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador,Long> {
    Optional<Entregador> findByCnpj(Long cnpj);
}
