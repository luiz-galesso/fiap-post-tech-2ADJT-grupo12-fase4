package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;


import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.*;
import static org.mockito.Mockito.*;

public class AumentaEstoqueProdutoTest {

    @Mock
    AumentaEstoqueProduto aumentaEstoqueProduto;

    @Mock
    ProdutoGateway produtoGateway;

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
    void devePermitirAumentarEstoqueDoProduto() {
        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(produtoGateway.findById(1L)).thenReturn(Optional.of(produto));
        when(aumentaEstoqueProduto.execute(
                any(Long.class),
                any(Long.class)
        )).thenReturn(produto);

        //act
        var resultado = aumentaEstoqueProduto.execute(1L, 10L);

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getCodProduto()).isEqualTo(produto.getCodProduto());
    }

    @Test
    void deveGerarExcecao_QuandoProdutoNaoEncontrado() {
        //act
        doThrow(new BussinessErrorException("Produto com id " + 1L + "não foi encontrado."))
                .when(aumentaEstoqueProduto).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> aumentaEstoqueProduto.execute(1L, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Produto com id " + 1L + "não foi encontrado.");
    }

    @Test
    void deveGerarExcecao_QuandoAQuantidadeForMenorIgualZero() {
        //act
        doThrow(new BussinessErrorException("Quantidade inválida."))
                .when(aumentaEstoqueProduto).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> aumentaEstoqueProduto.execute(1L, 0L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Quantidade inválida.");
    }

}
