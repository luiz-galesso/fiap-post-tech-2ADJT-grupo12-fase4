package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.enums.SituacaoEntrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.gateway.EntregaGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Entrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador.DesalocarRecursoEntregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.pedido.ConfirmaEntrega;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RealizaEntrega {

    private final EntregaGateway entregaGateway;
    private final DesalocarRecursoEntregador desalocarRecursoEntregador;
    private final ConfirmaEntrega confirmaEntrega;

    public Entrega execute(Long idEntrega, String nomeRecebedor) {

        Entrega entrega = entregaGateway.findById(idEntrega).orElseThrow(() -> new BusinessErrorException("Não foi possível obter a entrega"));

        desalocarRecursoEntregador.execute(entrega.getEntregador().getId());

        entrega.setSituacaoEntrega(SituacaoEntrega.ENTREGUE);
        entrega.setNomeRecebedor(nomeRecebedor);

        confirmaEntrega.execute(entrega.getIdPedido());

        return this.entregaGateway.create(entrega);

    }
}
