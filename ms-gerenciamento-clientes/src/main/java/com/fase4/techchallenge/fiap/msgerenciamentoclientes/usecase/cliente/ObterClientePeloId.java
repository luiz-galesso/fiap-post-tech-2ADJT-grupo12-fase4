package com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterClientePeloId {
    private final ClienteGateway clienteGateway;

    public ObterClientePeloId(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(String id) {
        return this.clienteGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não localizado"));
    }


}