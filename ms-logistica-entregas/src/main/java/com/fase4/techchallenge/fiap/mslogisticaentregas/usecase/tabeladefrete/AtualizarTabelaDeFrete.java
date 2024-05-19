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
        TabelaDeFrete tabelaDeFrete = tabelaDeFreteGateway.findById(idTabelaFrete).orElseThrow(()-> new BusinessErrorException("Não foi encontrado a tabela de frete cadastrada com o Id informado.") );

        if (!tabelaDeFrete.getEntregador().getId().equals(idEntregador)){
            throw new BusinessErrorException("Tabela de Frente não pertence ao entregador.");
        }

        tabelaDeFrete.setCepOrigem(tabelaDeFreteDTO.getCepOrigem());
        tabelaDeFrete.setCepDestinoInicial(tabelaDeFreteDTO.getCepDestinoInicial());
        tabelaDeFrete.setCepDestinoFinal(tabelaDeFreteDTO.getCepDestinoFinal());
        tabelaDeFrete.setValorFrete(tabelaDeFreteDTO.getValorFrete());
        tabelaDeFrete.setPrazoEntregaEmHoras(tabelaDeFreteDTO.getPrazoEntregaEmHoras());

        return this.tabelaDeFreteGateway.update(tabelaDeFrete);
    }
}
