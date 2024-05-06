package com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteGateway {

    private ClienteRepository clienteRepository;

    public ClienteGateway(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente create(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(String email) {
        return this.clienteRepository.findById(email);
    }

    public void remove(String email) {
        clienteRepository.deleteById(email);
    }

}