package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
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

public class RemoverProdutoPeloIdTest
{

    @Mock
    RemoverProdutoPeloId removerProdutoPeloId;

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
    void devePermitirRemoverProdutoPeloId() {

        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);

        when(removerProdutoPeloId.execute(any(Long.class))).thenReturn(true);

        //act
        var resultado =  removerProdutoPeloId.execute(produto.getCodProduto());

        //assert
        assertThat(resultado).isTrue();
    }

    @Test
    void deveGerarExcecao_QuandoProdutoNaoExistir() {

        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);

        //act
        doThrow(new BussinessErrorException("Não foi encontrado o produto cadastrado com o ID informado."))
                .when(removerProdutoPeloId).execute(any(Long.class));

        //assert
        assertThatThrownBy(() -> removerProdutoPeloId.execute(produto.getCodProduto()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o produto cadastrado com o ID informado.");
    }
}
