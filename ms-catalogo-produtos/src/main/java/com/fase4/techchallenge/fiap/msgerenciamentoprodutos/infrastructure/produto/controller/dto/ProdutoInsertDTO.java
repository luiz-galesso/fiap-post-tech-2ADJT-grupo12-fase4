package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

public record ProdutoInsertDTO(
        @NotNull
        String descricaoProduto,
        @NotNull
        String marca,
        @NotNull
        String categoria,
        @NotNull
        Long quantidade

) {


};
