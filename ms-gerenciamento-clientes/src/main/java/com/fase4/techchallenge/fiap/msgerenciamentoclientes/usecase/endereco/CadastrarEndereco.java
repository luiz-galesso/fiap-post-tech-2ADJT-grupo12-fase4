package com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.endereco;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.gateway.EnderecoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model.Endereco;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarEndereco {

    private final ClienteGateway  clienteGateway;
    private final EnderecoGateway enderecoGateway;

    public Endereco execute(String idCliente, EnderecoInsertDTO enderecoInsertDTO) {

        Optional<Cliente> clienteOptional = clienteGateway.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Cliente não existe.");
        }

        Endereco endereco =
                new Endereco(null,
                        clienteOptional.get(),
                        enderecoInsertDTO.getLogradouro(),
                        enderecoInsertDTO.getNumero(),
                        enderecoInsertDTO.getBairro(),
                        enderecoInsertDTO.getComplemento(),
                        enderecoInsertDTO.getCep(),
                        enderecoInsertDTO.getCidade(),
                        enderecoInsertDTO.getEstado(),
                        enderecoInsertDTO.getReferencia()
                );
        return this.enderecoGateway.create(endereco);
    }
}
