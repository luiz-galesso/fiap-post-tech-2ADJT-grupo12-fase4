package com.fase4.techchallenge.fiap.msgerenciamentoclientes.cliente.usecase;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente.RemoverClientePeloId;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.utils.ClienteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class RemoverClientePeloIdIT {

    @Autowired
    RemoverClientePeloId removerClientePeloId;
    @Autowired
    ClienteGateway clienteGateway;

    @Test
    void devePermitirRemoverCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);

        removerClientePeloId.execute(cliente.getEmail());
        var retorno = clienteGateway.findById(cliente.getEmail());
        assertThat(retorno).isEmpty();
    }

    @Test
    void deveGerarExcecao_QuandoRemoverCliente_EmailNaoExiste() {
        String emailCliente = "joao-silva@email.com";

        assertThatThrownBy(() -> removerClientePeloId.execute(emailCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
    }
}
