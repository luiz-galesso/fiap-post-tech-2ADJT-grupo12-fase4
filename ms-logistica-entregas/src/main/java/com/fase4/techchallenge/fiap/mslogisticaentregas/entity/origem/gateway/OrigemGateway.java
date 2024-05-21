package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.model.Origem;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.origem.repository.OrigemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrigemGateway {

    private final OrigemRepository origemRepository;

    public Optional<Origem> findById(Long id) { return this.origemRepository.findById(id);}


}
