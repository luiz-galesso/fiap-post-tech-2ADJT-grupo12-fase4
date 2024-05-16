package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.endereco;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Endereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.ClientesClient;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObtemEnderecoPeloIdClienteEIdEndereco {

    private final ClientesClient clientesClient;

    public Endereco execute(String email, Integer idEndereco) {
        try {
            return this.clientesClient.getEnderecoCliente(email, idEndereco);
        }
        catch(Exception e){
            throw new BussinessErrorException(e.getMessage());
        }
    }
}
