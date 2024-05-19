package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.produtoGerado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ObterProdutoPeloIdTest
{

    @Mock
    ObterProdutoPeloId obterProdutoPeloId;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirObterProdutoPeloId() {
        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(obterProdutoPeloId.execute(any(Long.class))).thenReturn(produto);

        //act
        var resultado = obterProdutoPeloId.execute(1L);

        //assert
        assertThat(resultado).isNotNull().isInstanceOf(Produto.class);
        assertThat(resultado.getCodProduto()).isEqualTo(1L);
    }

    @Test
    void deveGerarExcecao_QuandoOProdutoNaoForEncontrado() {
        //act
        doThrow(new EntityNotFoundException("Produto não localizado"))
                .when(obterProdutoPeloId).execute(any(Long.class));

        //assert
        assertThatThrownBy(() -> obterProdutoPeloId.execute(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não localizado");
    }

}
