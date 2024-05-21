package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class ProdutoHelper {

    public static ProdutoInsertDTO gerarProduto() {
        return new ProdutoInsertDTO(
                "Smartphone Galaxy S21",
                "Samsung",
                "Telefones",
                50L,
                2000D
        );
    }

    public static ProdutoInsertDTO gerarProduto(String descricao) {
        return new ProdutoInsertDTO(
                descricao,
                "Samsung",
                "Telefones",
                50L,
                2000D
        );
    }

    public static Produto produtoGerado(ProdutoInsertDTO pDTO) {
        return new Produto(
                pDTO.descricaoProduto(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade(),
                pDTO.valorUnitario(),
                LocalDateTime.now());
    }

    public static ProdutoUpdateDTO gerarProdutoUpdate(ProdutoInsertDTO pDTO)
    {
        return new ProdutoUpdateDTO(
                pDTO.descricaoProduto(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade(),
                pDTO.valorUnitario());
    }

    public static Produto produtoGerado(ProdutoInsertDTO pDTO, Long id) {
        return new Produto(
                id,
                pDTO.descricaoProduto(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade(),
                pDTO.valorUnitario(),
                LocalDateTime.now());
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }

}
