package com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.BussinessErrorException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RemoverClientePeloId {
    private final ClienteGateway clienteGateway;

    public RemoverClientePeloId(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public boolean execute(String id) {
        Optional<Cliente> clienteOptional = clienteGateway.findById(id);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o cliente cadastrado com o email informado.");
        }

        clienteGateway.remove(id);
        return true;
    }

}