package com.fase4.techchallenge.fiap.msgerenciamentoclientes.endereco.usecase;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.gateway.ClienteGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.gateway.EnderecoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model.Endereco;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.endereco.RemoverEnderecoPeloId;
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
class RemoverEnderecoPeloIdIT {

    @Autowired
    RemoverEnderecoPeloId removerEnderecoPeloId;
    @Autowired
    ClienteGateway clienteGateway;
    @Autowired
    EnderecoGateway enderecoGateway;

    @Test
    void devePermitirRemoverEndereco() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);
        enderecoGateway.create(endereco);

        removerEnderecoPeloId.execute(cliente.getEmail(), endereco.getId());
        var retorno = enderecoGateway.findById(endereco.getId());
        assertThat(retorno).isEmpty();
    }

    @Test
    void deveGerarExcecao_QuandoRemoverEndereco_ClienteNaoExiste() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);

        assertThatThrownBy(() -> removerEnderecoPeloId.execute(cliente.getEmail(), endereco.getId()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
    }

    @Test
    void deveGerarExcecao_QuandoRemoverEndereco_EnderecoNaoExiste() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);

        assertThatThrownBy(() -> removerEnderecoPeloId.execute(cliente.getEmail(), 11))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o endereco o id informado.");
    }

    @Test
    void deveGerarExcecao_QuandoRemoverEndereco_EnderecoOutroCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);

        assertThatThrownBy(() -> removerEnderecoPeloId.execute(cliente.getEmail(), endereco.getId()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Endereço não pertence ao cliente.");
    }

}
