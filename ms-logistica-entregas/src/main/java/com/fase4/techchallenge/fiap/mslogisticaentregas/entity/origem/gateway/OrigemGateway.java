package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository.EntregadorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrigemGateway {
    private final OrigemRepository origemRepository;

    public OrigemGateway(EntregadorRepository entregadorRepository) {
        this.origemRepository = entregadorRepository;
    }

    public Optional<Entregador> findById(Long id) { return this.origemRepository.findById(id);}


}
