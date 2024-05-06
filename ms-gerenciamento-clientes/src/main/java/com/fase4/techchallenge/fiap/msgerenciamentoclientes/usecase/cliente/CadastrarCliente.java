package com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarCliente {

    private final ClienteGateway clienteGateway;

    public Cliente execute(ClienteInsertDTO clienteDTO) {

        Optional<Cliente> clienteOptional = clienteGateway.findById(clienteDTO.getEmail());

        if (clienteOptional.isPresent()) {
            throw new BussinessErrorException("Já existe um cliente cadastrado com o email informado.");
        }

        Cliente cliente =
                new Cliente(clienteDTO.getEmail(),
                        clienteDTO.getNome(),
                        LocalDateTime.now(),
                        clienteDTO.getDataNascimento()
                );

        return this.clienteGateway.create(cliente);
    }
}
