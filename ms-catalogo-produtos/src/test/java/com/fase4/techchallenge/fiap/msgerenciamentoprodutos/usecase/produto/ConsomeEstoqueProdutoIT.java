package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ConsomeEstoqueProdutoIT {

    @Autowired
    ConsomeEstoqueProduto consomeEstoqueProduto;

    @Autowired
    CadastrarProduto cadastrarProduto;

    @Test
    void devePermitirConsumirEstoqueProduto() {
        //arrange
        Produto produto = cadastrarProduto.execute(gerarProduto());

        //act
        var resultado = consomeEstoqueProduto
                .execute(produto.getCodProduto(), 10L);

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getQuantidade()).isEqualTo(produto.getQuantidade());
    }

    @Test
    void deveGerarExcecao_QuandoProdutonaoEncontrato() {
        //assert
        assertThatThrownBy(() -> consomeEstoqueProduto
                .execute(888L, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Produto com id " + 888L + "não foi encontrado.");
    }

    @Test
    void deveGerarExcecao_QuandoQuantidadeInvalida() {
        Produto produto = cadastrarProduto.execute(gerarProduto("Produto " + new Random().nextLong()));
        //assert
        assertThatThrownBy(() -> consomeEstoqueProduto
                .execute(produto.getCodProduto(), 0L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Quantidade inválida.");
    }

    @Test
    void deveGerarExecao_QuandoEstoqueInsuficiente() {
        Produto produto = cadastrarProduto.execute(gerarProduto("Produto " + new Random().nextLong()));
        //assert
        assertThatThrownBy(() -> consomeEstoqueProduto
                .execute(produto.getCodProduto(), 1000L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Estoque insuficiente.");
    }
}
