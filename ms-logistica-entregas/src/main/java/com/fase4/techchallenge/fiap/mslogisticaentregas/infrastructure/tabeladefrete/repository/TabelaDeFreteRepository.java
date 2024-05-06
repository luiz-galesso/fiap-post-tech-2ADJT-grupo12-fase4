package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TabelaDeFreteRepository extends JpaRepository<TabelaDeFrete,Long> {

    public Optional<TabelaDeFrete> findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(@NotNull String cepOrigem, @NotNull String cepDestino, @NotNull Long idEntregador);

    public List<TabelaDeFrete> findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(@NotNull String cepOrigem, @NotNull String cepDestino, @NotNull Long quantidadeRecursosDisponiveis, @NotNull String situacao);

    public List<TabelaDeFrete> findAllByEntregador_Id(@NotNull Long idEntregador);

    public Optional<TabelaDeFrete> findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(@NotNull Long idEntregador, @NotNull String cepOrigem, @NotNull String cepDestino, @NotNull Long id);
}
