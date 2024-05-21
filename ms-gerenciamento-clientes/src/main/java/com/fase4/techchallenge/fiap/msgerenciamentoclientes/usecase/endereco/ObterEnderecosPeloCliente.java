package com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.endereco;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.gateway.EnderecoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model.Endereco;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ObterEnderecosPeloCliente {

    private final ClienteGateway clienteGateway;
    private final EnderecoGateway enderecoGateway;

    public List<Endereco> execute(String idCliente) {
        Optional<Cliente> clienteOptional = clienteGateway.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Cliente não existe.");
        }
        return this.enderecoGateway.findByCliente(clienteOptional.get());
    }
}
