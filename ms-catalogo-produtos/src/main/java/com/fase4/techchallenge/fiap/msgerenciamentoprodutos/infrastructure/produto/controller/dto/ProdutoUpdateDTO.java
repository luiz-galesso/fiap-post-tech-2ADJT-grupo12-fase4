package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProdutoUpdateDTO {

    private String codProduto;
    private String descricaoProduto;
    private String categoria;
    private Long quantidade;
    private LocalDateTime dataAtualizacao;
}
