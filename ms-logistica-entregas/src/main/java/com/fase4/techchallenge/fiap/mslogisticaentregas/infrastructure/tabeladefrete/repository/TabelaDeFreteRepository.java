package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TabelaDeFreteRepository extends JpaRepository<TabelaDeFrete,Long> {

    public Optional<TabelaDeFrete> findTabelaDeFreteByCepOrigemAndCepDestino(@NotNull String cepOrigem, @NotNull String cepDestino);
}
