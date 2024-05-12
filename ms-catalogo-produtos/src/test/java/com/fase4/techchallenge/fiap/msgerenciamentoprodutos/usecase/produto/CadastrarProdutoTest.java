package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.*;

public class CadastrarProdutoTest
{

    @Mock
    CadastrarProduto cadastrarProduto;

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
    void devePermitirCadastrarProduto() {

        //arrange
        ProdutoInsertDTO produtoDTO = gerarProduto();
        when(cadastrarProduto.execute(any(ProdutoInsertDTO.class))).thenReturn(produtoGerado(produtoDTO));

        //act
        var resultado = cadastrarProduto.execute(produtoDTO);

        //assert
        assertThat(resultado).isNotNull().isInstanceOf(Produto.class);
        assertThat(resultado.getDescricaoProduto()).isEqualTo(produtoDTO.descricaoProduto());
        assertThat(resultado.getDataAtualizacao()).isNotNull();

    }
}
