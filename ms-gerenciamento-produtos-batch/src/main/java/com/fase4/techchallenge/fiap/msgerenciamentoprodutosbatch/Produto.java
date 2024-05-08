package com.fase4.techchallenge.fiap.msgerenciamentoprodutosbatch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Produto
{
    private String descricaoProduto;

    private String marca;

    private String categoria;

    private Long quantidade;

    private LocalDateTime dataAtualizacao;

    public Produto() {}

    public Produto(String descricaoProduto, String marca, String categoria, String quantidade) {
        this.descricaoProduto = descricaoProduto;
        this.marca = marca;
        this.categoria = categoria;
        this.quantidade = Long.parseLong(quantidade);
    }
}
