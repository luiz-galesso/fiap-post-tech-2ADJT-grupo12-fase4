package com.fase4.techchallenge.fiap.msgerenciamentoclientes.endereco.usecase;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.gateway.EnderecoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model.Endereco;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.endereco.ObterEnderecosPeloCliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.utils.ClienteHelper;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.utils.EnderecoHelper;
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
class ObterEnderecosPeloClienteIT {

    @Autowired
    ObterEnderecosPeloCliente obterEnderecosPeloCliente;
    @Autowired
    ClienteGateway clienteGateway;
    @Autowired
    EnderecoGateway enderecoGateway;

    @Test
    void devePermitirObterEnderecosPorCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);
        enderecoGateway.create(endereco);

        var enderecoRetornado = obterEnderecosPeloCliente.execute(cliente.getEmail());
        assertThat(enderecoRetornado.get(0)).isInstanceOf(Endereco.class);
        assertThat(enderecoRetornado).hasSize(1);
    }

    @Test
    void deveGerarExcecao_QuandoObterEnderecosPorCliente_ClienteNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        Endereco endereco = EnderecoHelper.gerarEndereco(ClienteHelper.gerarCliente());

        assertThatThrownBy(() -> obterEnderecosPeloCliente.execute(emailCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente não existe.");
    }
}
