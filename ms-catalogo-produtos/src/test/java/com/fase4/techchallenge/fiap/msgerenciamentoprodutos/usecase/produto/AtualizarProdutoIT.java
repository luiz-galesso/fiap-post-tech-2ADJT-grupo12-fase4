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
import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class AtualizarProdutoIT
{

    @Autowired
    AtualizarProduto atualizarProduto;

    @Autowired
    CadastrarProduto cadastrarProduto;

    @Test
    void devePermitirAtualizarProduto() {
        //arrange
        Produto produto = cadastrarProduto.execute(gerarProduto());

        //act
        var resultado = atualizarProduto
                .execute(produto.getCodProduto(), gerarProdutoUpdate(gerarProduto()));

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isInstanceOf(Produto.class);
        assertThat(resultado.getCodProduto()).isEqualTo(produto.getCodProduto());
    }

    @Test
    void deveGerarExcecao_QuandoProdutoNaoEncontrado() {
        //assert
        assertThatThrownBy(() -> atualizarProduto
                .execute(1L, gerarProdutoUpdate(gerarProduto())))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o produto cadastrado com o identificador informado.");
    }
}
