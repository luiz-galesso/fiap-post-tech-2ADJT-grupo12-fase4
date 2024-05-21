package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorUpdateDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarEntregador {

    private final EntregadorGateway entregadorGateway;

    public Entregador execute(Long id, EntregadorUpdateDTO entregadorUpdateDTO){
        Optional<Entregador> entregadorOptional = entregadorGateway.findById(id);

        if ( entregadorOptional.isEmpty()) {
            throw new BusinessErrorException("Não foi encontrado o entregador com o Id informado");
        }
        Entregador entregador = new Entregador(
                entregadorUpdateDTO.getNome(),
                entregadorUpdateDTO.getCnpj(),
                entregadorUpdateDTO.getSituacao(),
                entregadorOptional.get().getQuantidadeRecursosDisponiveis()
                );
        entregador.setId(id);
        return this.entregadorGateway.update(entregador);
    }

}
