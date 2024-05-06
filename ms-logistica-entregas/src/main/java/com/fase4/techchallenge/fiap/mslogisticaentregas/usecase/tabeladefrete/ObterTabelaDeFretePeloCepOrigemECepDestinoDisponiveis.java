package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis {
    private final TabelaDeFreteGateway tabelaDeFreteGateway;
    public List<TabelaDeFrete> execute(String cepOrigem, String cepDestino){
        return this.tabelaDeFreteGateway.obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis(cepOrigem,cepDestino);
    }
}

