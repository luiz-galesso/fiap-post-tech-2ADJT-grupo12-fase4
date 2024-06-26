package com.fase4.techchallenge.fiap.msgerenciamentoclientes.endereco.usecase;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.gateway.EnderecoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model.Endereco;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.endereco.ObterEnderecoPeloId;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception.EntityNotFoundException;
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
class ObterEnderecoPeloIdIT {

    @Autowired
    ObterEnderecoPeloId obterEnderecoPeloId;
    @Autowired
    ClienteGateway clienteGateway;
    @Autowired
    EnderecoGateway enderecoGateway;

    @Test
    void devePermitirObterEndereco() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);
        enderecoGateway.create(endereco);


        var enderecoRetornado = obterEnderecoPeloId.execute(cliente.getEmail(), endereco.getId());
        assertThat(enderecoRetornado).isInstanceOf(Endereco.class);
        assertThat(enderecoRetornado.getCliente()).isNotNull();
        assertThat(enderecoRetornado.getId()).isNotNull();
    }

    @Test
    void deveGerarExcecao_QuandoObterEndereco_ClienteNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        Endereco endereco = EnderecoHelper.gerarEndereco(ClienteHelper.gerarCliente());

        assertThatThrownBy(() -> obterEnderecoPeloId.execute(emailCliente,endereco.getId()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente não existe.");
    }

    @Test
    void deveGerarExcecao_QuandoObterEndereco_EnderecoNaoExiste() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);

        assertThatThrownBy(() -> obterEnderecoPeloId.execute(cliente.getEmail(),11))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Endereço não localizado");
    }

    @Test
    void deveGerarExcecao_QuandoObterEndereco_EnderecoOutroCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);

        assertThatThrownBy(() -> obterEnderecoPeloId.execute(cliente.getEmail(),endereco.getId()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Endereço não pertence ao cliente.");
    }
}
