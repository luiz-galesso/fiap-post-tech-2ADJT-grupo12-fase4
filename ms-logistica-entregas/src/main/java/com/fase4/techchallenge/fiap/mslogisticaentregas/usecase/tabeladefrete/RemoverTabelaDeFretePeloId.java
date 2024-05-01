package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RemoverTabelaDeFretePeloId {

    private final TabelaDeFreteGateway tabelaDeFreteGateway;

    public RemoverTabelaDeFretePeloId(TabelaDeFreteGateway tabelaDeFreteGateway){
        this.tabelaDeFreteGateway = tabelaDeFreteGateway;
    }

    public boolean execute(Long id) {
        Optional<TabelaDeFrete> tabelaDeFreteOptional = tabelaDeFreteGateway.findById(id);

        if (tabelaDeFreteOptional.isEmpty()){
            throw new BusinessErrorException("Não foi encontrada tabela de frete com o id informado");
        }
        tabelaDeFreteGateway.remove(id);
        return true;
    }
}
