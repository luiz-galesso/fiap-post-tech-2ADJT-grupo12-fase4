package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class AumentaEstoqueProdutoIT {
    @Autowired
    AumentaEstoqueProduto aumentaEstoqueProduto;

    @Autowired
    CadastrarProduto cadastrarProduto;

    @Test
    void devePermitirAumentarEstoqueDoProduto() {
        //arrange
        Produto produto = cadastrarProduto.execute(gerarProduto());

        //act
        var resultado = aumentaEstoqueProduto
                .execute(produto.getCodProduto(), 10L);

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getQuantidade()).isEqualTo(produto.getQuantidade());
    }

    @Test
    void deveGerarExcecao_QuandoNaoEncontrado() {
        //assert
        assertThatThrownBy(() -> aumentaEstoqueProduto
                .execute(888L, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Produto com id " + 888L + "não foi encontrado.");
    }

    @Test
    void deveGerarExcecao_QuandoAQuantidadeForMenorIgualZero() {
        //arrange
        Produto produto = cadastrarProduto.execute(gerarProduto());

        //assert
        assertThatThrownBy(() -> aumentaEstoqueProduto
                .execute(produto.getCodProduto(), 0L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Quantidade inválida.");
    }
}
