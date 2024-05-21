package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.utils;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository.TabelaDeFreteRepository;

public class TabelaDeFreteHelper {
    public static TabelaDeFrete gerarTabelaDeFrete(Long id) {
        return TabelaDeFrete.builder()
                .id(id)
                .entregador(Entregador.builder()
                        .id(1L)
                        .cnpj(52123949000182L)
                        .nome("Rapidão Entregas")
                        .situacao("ATIVO")
                        .quantidadeRecursosDisponiveis(8L)
                        .build())
                .cepOrigem(84015030L)
                .cepDestinoInicial(70000000L)
                .cepDestinoFinal(80000000L)
                .valorFrete(80.25)
                .prazoEntregaEmHoras(6L)
                .build();
    }
    public static TabelaDeFrete registrarTabelaDeFrete(TabelaDeFreteRepository tabelaDeFreteRepository, TabelaDeFrete tabelaDeFrete){
        return tabelaDeFreteRepository.save(tabelaDeFrete);
    }

}
