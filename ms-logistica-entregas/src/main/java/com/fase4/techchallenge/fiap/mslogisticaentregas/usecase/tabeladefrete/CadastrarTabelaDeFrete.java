package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
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
    private final EntregadorGateway entregadorGateway;

    public TabelaDeFrete execute(Long idEntregador, TabelaDeFreteInsertDTO tabelaDeFreteDTO) {
        Optional<TabelaDeFrete> tabelaDeFreteOptional = tabelaDeFreteGateway.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador(tabelaDeFreteDTO.getCepOrigem(), tabelaDeFreteDTO.getCepDestino(), idEntregador);

        if (tabelaDeFreteOptional.isPresent()) {
            throw new BusinessErrorException("Já existe uma tabela de frete do entregador para a Origem x Destino informados");
        }

        Optional<Entregador> entregadorOptional = entregadorGateway.findById(idEntregador);

        if (entregadorOptional.isEmpty()) {
            throw new BusinessErrorException("Entregador não encontrado");
        }

        TabelaDeFrete tabelaDeFrete = new TabelaDeFrete(tabelaDeFreteDTO.getCepOrigem(),
                tabelaDeFreteDTO.getCepDestino(),
                tabelaDeFreteDTO.getValorFrete(),
                tabelaDeFreteDTO.getPrazoEntregaEmHoras(),
                entregadorOptional.get());
        return this.tabelaDeFreteGateway.create(tabelaDeFrete);
    }
}
