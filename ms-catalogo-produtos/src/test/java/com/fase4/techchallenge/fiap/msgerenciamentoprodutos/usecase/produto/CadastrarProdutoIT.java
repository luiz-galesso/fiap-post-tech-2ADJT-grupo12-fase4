package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class CadastrarProdutoIT
{

    @Autowired
    CadastrarProduto cadastrarProduto;

    @Test
    void devePermitirCadastrarProduto() {

        //arrange
        ProdutoInsertDTO produtoDTO = ProdutoHelper.gerarProduto();

        //act
        var resultado = cadastrarProduto.execute(produtoDTO);

        //assert
        assertThat(resultado).isNotNull().isInstanceOf(Produto.class);
        assertThat(resultado.getCodProduto()).isNotNull();
        assertThat(resultado.getDataAtualizacao()).isNotNull();



    }

}
