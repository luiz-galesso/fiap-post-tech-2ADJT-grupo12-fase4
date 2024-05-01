package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterTabelaDeFretePeloId {
    private final TabelaDeFreteGateway tabelaDeFreteGateway;

    public ObterTabelaDeFretePeloId(TabelaDeFreteGateway tabelaDeFreteGateway) {
        this.tabelaDeFreteGateway = tabelaDeFreteGateway;}


    public TabelaDeFrete execute(Long id) {
        return this.tabelaDeFreteGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Tabela de frete não localizada"));
    }
}
