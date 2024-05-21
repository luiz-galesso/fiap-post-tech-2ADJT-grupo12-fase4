package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository.EntregadorRepository;

public class EntregadorHelper {
    public static Entregador gerarEntregador(Long id) {
        return Entregador.builder()
                .id(id)
                .cnpj(52123949000182L)
                .nome("Rapidão Entregas")
                .situacao("ATIVO")
                .quantidadeRecursosDisponiveis(8L)
                .build();

    }

    public static Entregador registrarEntregador(EntregadorRepository entregadorRepository, Entregador entregador) {
        return entregadorRepository.save(entregador);
    }
}
