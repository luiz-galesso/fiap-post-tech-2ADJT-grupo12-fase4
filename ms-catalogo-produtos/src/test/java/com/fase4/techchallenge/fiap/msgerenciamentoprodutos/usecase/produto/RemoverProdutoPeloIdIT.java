package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.produtoGerado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class RemoverProdutoPeloIdIT
{
    @Autowired
    RemoverProdutoPeloId removerProdutoPeloId;

    @Autowired
    CadastrarProduto cadastrarProduto;

    @Test
    void devePermitirRemoverProdutoPeloId() {
        //arrange
        Produto produto = cadastrarProduto.execute(gerarProduto());

        //act
        var resultado = removerProdutoPeloId.execute(produto.getCodProduto());

        //assert
        assertThat(resultado).isTrue();
    }

    @Test
    void deveGerarExcecao_QuandoProdutoNaoExistir() {
        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1010L);

        //assert
        assertThatThrownBy(() -> removerProdutoPeloId.execute(produto.getCodProduto())).
                isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o produto cadastrado com o ID informado.");
    }

}
