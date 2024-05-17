package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Entrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.repository.EntregaRepository;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository.EntregadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class EntregaGateway {
    private final EntregaRepository entregaRepository;

    public Entrega create(Entrega entrega) { return  this.entregaRepository.save(entrega);}
    public Entrega update(Entrega entrega) { return this.entregaRepository.save(entrega);}
    public Optional<Entrega> findById(Long id) { return this.entregaRepository.findById(id);}

    public Optional<Entrega> findByIdPedido(Long id) {
        return this.entregaRepository.findById(id);
    }
}
