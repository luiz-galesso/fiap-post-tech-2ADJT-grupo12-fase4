package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RemoverTabelaDeFretePeloId {

    private final TabelaDeFreteGateway tabelaDeFreteGateway;

    public boolean execute(Long id) {
        Optional<TabelaDeFrete> tabelaDeFreteOptional = tabelaDeFreteGateway.findById(id);

        if (tabelaDeFreteOptional.isEmpty()){
            throw new BusinessErrorException("Não foi encontrada tabela de frete com o id informado");
        }
        tabelaDeFreteGateway.remove(id);
        return true;
    }
}
