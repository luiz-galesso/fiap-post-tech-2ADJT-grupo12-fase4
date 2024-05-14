package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.produtoGerado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AtualizarEstoqueProdutoTest
{
    @Mock
    AtualizarEstoqueProduto atualizarEstoqueProduto;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirAtualizarEstoqueDoProduto() {
        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);
        Long novaQuantidade = 10L;

        when(atualizarEstoqueProduto.execute(any(Long.class), any(Long.class))).thenReturn(produto);

        //act
        var resultado = atualizarEstoqueProduto.execute(1L, novaQuantidade);

        //assert
        assertThat(resultado).isNotNull().isInstanceOf(Produto.class);
        assertThat(resultado.getCodProduto()).isEqualTo(1L);
        assertThat(resultado.getQuantidade()).isEqualTo(40L);
    }

}
