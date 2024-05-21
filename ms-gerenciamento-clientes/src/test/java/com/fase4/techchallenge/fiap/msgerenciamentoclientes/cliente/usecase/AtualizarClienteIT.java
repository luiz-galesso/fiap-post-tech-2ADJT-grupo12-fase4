package com.fase4.techchallenge.fiap.msgerenciamentoclientes.cliente.usecase;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente.AtualizarCliente;
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
class AtualizarClienteIT {

    @Autowired
    AtualizarCliente atualizarCliente;
    @Autowired
    ClienteGateway clienteGateway;

    @Test
    void devePermitirAtualizarCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);
        ClienteUpdateDTO clienteUpdateDTO = ClienteHelper.gerarClienteUpdate();

        var clienteRetornado = atualizarCliente.execute(cliente.getEmail(), clienteUpdateDTO);

        assertThat(clienteRetornado).isInstanceOf(Cliente.class);
        assertThat(clienteRetornado.getEmail()).isNotNull();
        assertThat(clienteRetornado.getDataRegistro()).isNotNull();
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarCliente_ClienteNaoCadastrado() {
        String emailCliente = "john_wick@email.com";
        ClienteUpdateDTO clienteUpdateDTO = ClienteHelper.gerarClienteUpdate();

        assertThatThrownBy(() -> atualizarCliente.execute(emailCliente, clienteUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
    }
}
