package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto.TabelaDeFreteUpdateDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarTabelaDeFrete {
    private final TabelaDeFreteGateway tabelaDeFreteGateway;

    public TabelaDeFrete execute(Long id, TabelaDeFreteUpdateDTO tabelaDeFreteDTO){
        Optional<TabelaDeFrete> tabelaDeFreteOptional = tabelaDeFreteGateway.findTabelaDeFreteByCepOrigemAndCepDestino(tabelaDeFreteDTO.getCepOrigem(), tabelaDeFreteDTO.getCepDestino());

        if (tabelaDeFreteOptional.isEmpty()){
            throw new BusinessErrorException("Não foi encontrado a tabela de frete cadastrada com a Origem x Destino informada.");
        }

        TabelaDeFrete tabelaDeFrete = new TabelaDeFrete(id,
                tabelaDeFreteDTO.getCepOrigem(),
                tabelaDeFreteDTO.getCepDestino(),
                tabelaDeFreteDTO.getValorFrete(),
                tabelaDeFreteDTO.getPrazoEntregaEmHoras());
        return this.tabelaDeFreteGateway.update(tabelaDeFrete);
    }
}
