package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

public record ProdutoUpdateDTO (String descricaoProduto, String marca, String categoria, Long quantidade){

};
