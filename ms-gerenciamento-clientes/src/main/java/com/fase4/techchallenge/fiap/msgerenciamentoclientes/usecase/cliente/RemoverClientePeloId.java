package com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model.Endereco;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.endereco.ObterEnderecosPeloCliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.endereco.RemoverEnderecoPeloId;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RemoverClientePeloId {
    private final ClienteGateway clienteGateway;

    private final ObterEnderecosPeloCliente obterEnderecosPeloCliente;

    private final RemoverEnderecoPeloId removerEnderecoPeloId;

    public void execute(String id) {
        Optional<Cliente> clienteOptional = clienteGateway.findById(id);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o cliente cadastrado com o email informado.");
        }

        List<Endereco> enderecoList = obterEnderecosPeloCliente.execute(clienteOptional.get().getEmail());
        enderecoList.forEach( e -> removerEnderecoPeloId.execute(clienteOptional.get().getEmail(), e.getId()));

        clienteGateway.remove(id);
    }

}