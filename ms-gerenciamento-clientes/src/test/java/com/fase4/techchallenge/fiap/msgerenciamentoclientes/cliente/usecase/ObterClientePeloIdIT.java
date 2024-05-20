package com.fase4.techchallenge.fiap.msgerenciamentoclientes.cliente.usecase;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente.ObterClientePeloId;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.EntityNotFoundException;
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
class ObterClientePeloIdIT {

    @Autowired
    ObterClientePeloId obterClientePeloId;
    @Autowired
    ClienteGateway clienteGateway;

    @Test
    void devePermitirObterCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);

        var clienteRetornado = obterClientePeloId.execute(cliente.getEmail());
        assertThat(clienteRetornado).isInstanceOf(Cliente.class);
        assertThat(clienteRetornado.getEmail()).isNotNull();
        assertThat(clienteRetornado.getDataRegistro()).isNotNull();
    }

    @Test
    void deveGerarExcecao_QuandoObterCliente_EmailNaoExiste() {
        String emailCliente = "joao-silva@email.com";

        assertThatThrownBy(() -> obterClientePeloId.execute(emailCliente))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cliente não localizado");
    }
}
