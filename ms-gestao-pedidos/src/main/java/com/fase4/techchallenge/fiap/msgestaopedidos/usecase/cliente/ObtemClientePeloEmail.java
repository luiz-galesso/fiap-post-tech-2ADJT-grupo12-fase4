package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.cliente;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Cliente;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.ClientesClient;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
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
            throw new BussinessErrorException(e.getMessage());
        }
    }
}
