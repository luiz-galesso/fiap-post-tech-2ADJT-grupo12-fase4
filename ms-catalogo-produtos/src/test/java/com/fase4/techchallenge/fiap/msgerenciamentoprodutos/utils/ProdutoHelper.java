package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;

import java.time.LocalDateTime;

public class ProdutoHelper {

    public static ProdutoInsertDTO gerarProduto() {
        return new ProdutoInsertDTO(
                "Smartphone Galaxy S21",
                "Samsung",
                "Telefones",
                50L
        );
    }

    public static Produto produtoGerado(ProdutoInsertDTO pDTO) {
        return new Produto(
                pDTO.descricaoProduto(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade(),
                LocalDateTime.now());
    }

    public static ProdutoUpdateDTO gerarProdutoUpdate(ProdutoInsertDTO pDTO)
    {
        return new ProdutoUpdateDTO(
                pDTO.descricaoProduto(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade());
    }

    public static Produto produtoGerado(ProdutoInsertDTO pDTO, Long id) {
        return new Produto(
                id,
                pDTO.descricaoProduto(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade(),
                LocalDateTime.now());
    }

}
