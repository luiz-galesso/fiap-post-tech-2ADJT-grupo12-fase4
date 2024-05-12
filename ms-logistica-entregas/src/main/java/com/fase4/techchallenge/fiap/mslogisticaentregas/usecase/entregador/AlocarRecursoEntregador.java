package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AlocarRecursoEntregador {
    private final EntregadorGateway entregadorGateway;

    public Entregador execute(Long idEntregador){

        Optional<Entregador> entregadorOptional = this.entregadorGateway.findById(idEntregador);

        if (entregadorOptional.isEmpty()) {
            throw new BusinessErrorException("Entregador não localizado");
        }

        Entregador entregadorDesatualizado = entregadorOptional.get();

        if (entregadorDesatualizado.getQuantidadeRecursosDisponiveis().compareTo(0L) == 0){
            throw new BusinessErrorException("Entregador sem recurso disponivel para alocação");
        }

        Entregador entregador = new Entregador(entregadorDesatualizado.getId(),
                entregadorDesatualizado.getNome(),
                entregadorDesatualizado.getCnpj(),
                entregadorDesatualizado.getSituacao(),
                entregadorDesatualizado.getQuantidadeRecursosDisponiveis() - 1L);
        return entregadorGateway.update(entregador);

    }
}
