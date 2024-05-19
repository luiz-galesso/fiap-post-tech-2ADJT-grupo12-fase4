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

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.produtoGerado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ConsomeEstoqueProdutoTest {

    @Mock
    ConsomeEstoqueProduto consomeEstoqueProduto;

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
    void devePermitirConsumirEstoqueProduto() {
        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(produtoGateway.findById(1L)).thenReturn(Optional.of(produto));
        when(consomeEstoqueProduto.execute(
                any(Long.class),
                any(Long.class)
        )).thenReturn(produto);

        //act
        var resultado = consomeEstoqueProduto.execute(1L, 10L);

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getCodProduto()).isEqualTo(produto.getCodProduto());
    }

    @Test
    void deveGerarExcecao_QuandoProdutonaoEncontrato() {
        //act
        Long id = 2L;

        doThrow(new BussinessErrorException("Produto com id " + id + "não foi encontrado."))
                .when(consomeEstoqueProduto).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> consomeEstoqueProduto.execute(id, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Produto com id " + id + "não foi encontrado.");
    }

    @Test
    void deveGerarExcecao_QuandoQuantidadeInvalida() {
        //act
        Long id = 1L;

        doThrow(new BussinessErrorException("Quantidade inválida."))
                .when(consomeEstoqueProduto).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> consomeEstoqueProduto.execute(id, 0L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Quantidade inválida.");
    }

    @Test
    void deveGerarExecao_QuandoEstoqueInsuficiente() {
        //act
        Long id = 2L;

        doThrow(new BussinessErrorException("Estoque insuficiente."))
                .when(consomeEstoqueProduto).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> consomeEstoqueProduto.execute(id, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Estoque insuficiente.");
    }
}
