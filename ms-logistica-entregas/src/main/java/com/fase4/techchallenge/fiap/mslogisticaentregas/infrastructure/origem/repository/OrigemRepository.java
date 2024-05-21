package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.origem.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.model.Origem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrigemRepository extends JpaRepository<Origem,Long> {

}
