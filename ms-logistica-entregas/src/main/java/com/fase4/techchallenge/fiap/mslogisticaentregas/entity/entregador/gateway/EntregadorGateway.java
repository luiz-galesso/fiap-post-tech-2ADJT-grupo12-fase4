package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository.EntregadorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntregadorGateway {
    private final EntregadorRepository entregadorRepository;

    public EntregadorGateway(EntregadorRepository entregadorRepository) {
        this.entregadorRepository = entregadorRepository;
    }
    public Entregador create(Entregador entregador) { return  this.entregadorRepository.save(entregador);}
    public Entregador update(Entregador entregador) { return this.entregadorRepository.save(entregador);}
    public Optional<Entregador> findById(Long id) { return this.entregadorRepository.findById(id);}

    public Optional<Entregador> findByCnpj(Long cnpj) { return this.entregadorRepository.findByCnpj(cnpj);}

}
