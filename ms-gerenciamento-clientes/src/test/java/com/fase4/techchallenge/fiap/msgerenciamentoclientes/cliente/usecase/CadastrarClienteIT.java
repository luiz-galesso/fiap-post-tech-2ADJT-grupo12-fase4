package com.fase4.techchallenge.fiap.msgerenciamentoclientes.cliente.usecase;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente.CadastrarCliente;
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
class CadastrarClienteIT {

    @Autowired
    CadastrarCliente cadastrarCliente;
    @Autowired
    ClienteGateway clienteGateway;

    @Test
    void devePermitirCadastrarCliente() {
        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();

        var clienteRetornado = cadastrarCliente.execute(clienteInsertDTO);

        assertThat(clienteRetornado).isInstanceOf(Cliente.class);
        assertThat(clienteRetornado.getEmail()).isNotNull();
        assertThat(clienteRetornado.getDataRegistro()).isNotNull();
    }

    @Test
    void deveGerarExcecao_QuandoCadastrarCliente_ClienteJaCadastrado() {
        Cliente cliente = ClienteHelper.gerarCliente();
        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        clienteGateway.create(cliente);

        assertThatThrownBy(() -> cadastrarCliente.execute(clienteInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Já existe um cliente cadastrado com o email informado.");
    }
}
