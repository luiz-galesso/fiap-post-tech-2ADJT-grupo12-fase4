package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto.TabelaDeFreteInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarTabelaDeFrete {
    private final TabelaDeFreteGateway tabelaDeFreteGateway;

    public TabelaDeFrete execute(TabelaDeFreteInsertDTO tabelaDeFreteDTO) {
        Optional<TabelaDeFrete> tabelaDeFreteOptional = tabelaDeFreteGateway.findTabelaDeFreteByCepOrigemAndCepDestino(tabelaDeFreteDTO.getCepOrigem(), tabelaDeFreteDTO.getCepDestino());

        if (tabelaDeFreteOptional.isPresent()) {
            throw new BusinessErrorException("Já existe uma tabela de frete para a Origem x Destino informada");
        }
        TabelaDeFrete tabelaDeFrete = new TabelaDeFrete(tabelaDeFreteDTO.getCepOrigem(),
                tabelaDeFreteDTO.getCepDestino(),
                tabelaDeFreteDTO.getValorFrete(),
                tabelaDeFreteDTO.getPrazoEntregaEmHoras());
        return this.tabelaDeFreteGateway.create(tabelaDeFrete);
    }
}
