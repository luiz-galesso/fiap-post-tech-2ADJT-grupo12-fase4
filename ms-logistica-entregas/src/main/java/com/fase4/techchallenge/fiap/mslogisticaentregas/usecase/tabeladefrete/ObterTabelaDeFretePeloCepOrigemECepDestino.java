package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObterTabelaDeFretePeloCepOrigemECepDestino {
    private final TabelaDeFreteGateway tabelaDeFreteGateway;

    public TabelaDeFrete execute(String cepOrigem, String cepDestino, Long idEntregador){
        return this.tabelaDeFreteGateway.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador(cepOrigem,cepDestino,idEntregador).orElseThrow( () -> new EntityNotFoundException("Tabela de frete não localizada"));
    }
}

