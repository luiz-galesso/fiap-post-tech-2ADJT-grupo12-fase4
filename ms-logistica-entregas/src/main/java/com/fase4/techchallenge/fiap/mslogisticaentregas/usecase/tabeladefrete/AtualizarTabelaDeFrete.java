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

    public TabelaDeFrete execute(Long idEntregador, Long idTabelaFrete, TabelaDeFreteUpdateDTO tabelaDeFreteDTO){
        Optional<TabelaDeFrete> tabelaDeFreteOptional = tabelaDeFreteGateway.findById(idTabelaFrete);

        if (tabelaDeFreteOptional.isEmpty()){
            throw new BusinessErrorException("Não foi encontrado a tabela de frete cadastrada com o Id informado.");
        }

        if (!tabelaDeFreteOptional.get().getEntregador().getId().equals(idEntregador)){
            throw new BusinessErrorException("Tabela de Frente não pertence ao entregador.");
        }

        Optional<TabelaDeFrete> outraTabelaExistente = tabelaDeFreteGateway.findTabelaDeFreteByIdEntregadorAndCepOrigemAndCepDestinoAndIdTabelaNot(idEntregador,
                tabelaDeFreteDTO.getCepOrigem(),
                tabelaDeFreteDTO.getCepDestino(),
                idTabelaFrete);

        if (outraTabelaExistente.isPresent()){
            throw new BusinessErrorException("Já existe outra Tabela de Frente para este entregador com a mesma Origem e Destino.");
        }

        TabelaDeFrete tabelaDeFrete = new TabelaDeFrete(idTabelaFrete,
                tabelaDeFreteDTO.getCepOrigem(),
                tabelaDeFreteDTO.getCepDestino(),
                tabelaDeFreteDTO.getValorFrete(),
                tabelaDeFreteDTO.getPrazoEntregaEmHoras(),
                tabelaDeFreteOptional.get().getEntregador());
        return this.tabelaDeFreteGateway.update(tabelaDeFrete);
    }
}
