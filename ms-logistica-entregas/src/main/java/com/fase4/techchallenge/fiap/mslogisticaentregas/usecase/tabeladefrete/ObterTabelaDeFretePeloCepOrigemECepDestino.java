package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterTabelaDeFretePeloCepOrigemECepDestino {
    private final TabelaDeFreteGateway tabelaDeFreteGateway;

    public ObterTabelaDeFretePeloCepOrigemECepDestino(TabelaDeFreteGateway tabelaDeFreteGateway){
        this.tabelaDeFreteGateway = tabelaDeFreteGateway;
    }

    public TabelaDeFrete execute(String cepOrigem, String cepDestino){
        return this.tabelaDeFreteGateway.findTabelaDeFreteByCepOrigemAndCepDestino(cepOrigem,cepDestino).orElseThrow( () -> new EntityNotFoundException("Tabela de frete não localizada"));
    }
}

