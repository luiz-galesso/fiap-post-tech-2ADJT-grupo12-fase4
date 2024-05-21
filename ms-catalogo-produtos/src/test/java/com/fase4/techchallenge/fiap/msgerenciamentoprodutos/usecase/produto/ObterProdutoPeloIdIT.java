package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ObterProdutoPeloIdIT
{
    @Autowired
    ObterProdutoPeloId obterProdutoPeloId;

    @Autowired
    CadastrarProduto cadastrarProduto;

    @Test
    void devePermitirObterProdutoPeloId() {
        //arrange
        Produto produto = cadastrarProduto.execute(gerarProduto());

        //act
        var resultado = obterProdutoPeloId.execute(produto.getCodProduto());

        //assert
        assertThat(resultado).isNotNull().isInstanceOf(Produto.class);
        assertThat(resultado.getCodProduto()).isEqualTo(produto.getCodProduto());
    }

    @Test
    void deveGerarExcecao_QuandoOProdutoNaoForEncontrado() {
        //assert
        assertThatThrownBy(() -> obterProdutoPeloId.execute(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não localizado");
    }
}
