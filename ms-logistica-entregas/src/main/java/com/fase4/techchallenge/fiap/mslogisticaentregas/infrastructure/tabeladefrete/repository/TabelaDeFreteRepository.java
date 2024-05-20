package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TabelaDeFreteRepository extends JpaRepository<TabelaDeFrete,Long> {

    Optional<TabelaDeFrete> findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(Long cepOrigem, Long cepDestinoInicial, Long cepDestinoFinal, Entregador entregador);

    List<TabelaDeFrete> findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(Long cepOrigem, Long cep1, Long cep2);
    //public Optional<TabelaDeFrete> findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(@NotNull Long cepOrigem, @NotNull Long cepDestino, @NotNull Long idEntregador);

    //public List<TabelaDeFrete> findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(@NotNull Long cepOrigem, @NotNull Long cepDestino, @NotNull Long quantidadeRecursosDisponiveis, @NotNull String situacao);

   // public List<TabelaDeFrete> findAllByEntregador_Id(@NotNull Long idEntregador);

    //public Optional<TabelaDeFrete> findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(@NotNull Long idEntregador, @NotNull Long cepOrigem, @NotNull Long cepDestino, @NotNull Long id);
}
