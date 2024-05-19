package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.enums.SituacaoEntrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.gateway.EntregaGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Endereco;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Entrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.gateway.OrigemGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.model.Origem;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller.dto.EntregaInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.cliente.ObtemClientePeloEmail;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador.AlocarRecursoEntregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmRotaDeEntrega {

    private final EntregaGateway entregaGateway;

    public Entrega execute(Long idEntrega) {

        Entrega entrega = entregaGateway.findById(idEntrega).orElseThrow(() -> new BusinessErrorException("Não foi possível obter a entrega"));

        entrega.setSituacaoEntrega(SituacaoEntrega.EM_ROTA_DE_ENTREGA);

        return this.entregaGateway.create(entrega);

    }
}
