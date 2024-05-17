package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.cliente;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.feign.ClientesClient;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObtemClientePeloEmail {

    private final ClientesClient clientesClient;

    public Cliente execute(String email) {
        try {
            return this.clientesClient.getCliente(email);
        }
        catch( Exception e){
            throw new BusinessErrorException(e.getMessage());
        }
    }
}
