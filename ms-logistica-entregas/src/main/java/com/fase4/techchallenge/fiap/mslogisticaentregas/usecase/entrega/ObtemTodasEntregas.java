package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.gateway.EntregaGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Entrega;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtemTodasEntregas {

    private final EntregaGateway entregaGateway;

    public List<Entrega> execute() {

        return this.entregaGateway.findAll();

    }
}
