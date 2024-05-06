package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObterEntregadorPeloId {
    private final EntregadorGateway entregadorGateway;


    public Entregador execute(Long id){
        return this.entregadorGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Entregador não localizado"));
    }
}
