package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.gateway.OrigemGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.model.Origem;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis {
    private final TabelaDeFreteGateway tabelaDeFreteGateway;
    private final OrigemGateway origemGateway;
    public List<TabelaDeFrete> execute(Long cepDestino){

        Origem origem = origemGateway.findById(1L).orElseThrow(() ->new BusinessErrorException("Não foi possível obter a origem do pedido"));

        return this.tabelaDeFreteGateway.findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(origem.getCep(),cepDestino);
    }
}

