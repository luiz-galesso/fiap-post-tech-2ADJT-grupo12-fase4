package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AtualizarProdutoTest
{
    @Mock
    AtualizarProduto atualizarProduto;

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
    void devePermitirAtualizarProduto() {
        //arrange
        ProdutoUpdateDTO produtoUpdateDTO = gerarProdutoUpdate(gerarProduto());
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(atualizarProduto.execute(any(Long.class), any(ProdutoUpdateDTO.class)))
                .thenReturn(produto);

        //act
        var resultado = atualizarProduto.execute(1L, produtoUpdateDTO);

        //assert
        assertThat(resultado).isNotNull().isInstanceOf(Produto.class);
        assertThat(resultado.getCodProduto()).isEqualTo(produto.getCodProduto());
    }

    @Test
    void deveGerarExcecao_QuandoProdutoNaoEncontrado() {
        //act
        doThrow(new BussinessErrorException("Não foi encontrado o produto cadastrado com o identificador informado."))
                .when(atualizarProduto).execute(any(Long.class), any(ProdutoUpdateDTO.class));

        //assert
        assertThatThrownBy(() -> atualizarProduto.execute(1L, gerarProdutoUpdate(gerarProduto())))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o produto cadastrado com o identificador informado.");
    }
}
