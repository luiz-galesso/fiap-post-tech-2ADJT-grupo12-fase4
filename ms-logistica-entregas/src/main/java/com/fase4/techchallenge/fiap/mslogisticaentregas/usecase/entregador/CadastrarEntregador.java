package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarEntregador {
    private final EntregadorGateway entregadorGateway;

    public Entregador execute(EntregadorInsertDTO entregadorInsertDTO) {
        Optional<Entregador> entregadorOptional = entregadorGateway.findByCnpj(entregadorInsertDTO.getCnpj());
        if (entregadorOptional.isPresent()) {
            throw new BusinessErrorException("Já existe um entregador com o cnpj informado");
        }

        if ((entregadorInsertDTO.getQuantidadeRecursosDisponiveis().compareTo(0L) <= 0)) {
            throw new BusinessErrorException("Quantidade de Recursos deve ser maior que zero");
        }
        Entregador entregador = new Entregador(entregadorInsertDTO.getNome(),
                entregadorInsertDTO.getCnpj(),
                "ATIVO",
                entregadorInsertDTO.getQuantidadeRecursosDisponiveis());
        return this.entregadorGateway.create(entregador);

    }

}
