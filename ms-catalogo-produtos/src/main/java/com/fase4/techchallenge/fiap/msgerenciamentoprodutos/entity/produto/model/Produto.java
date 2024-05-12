package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long codProduto;
    @NotBlank
    @Column(unique = true)
    private String descricaoProduto;
    @NotBlank
    private String marca;
    @NotBlank
    private String categoria;
    @NotNull
    private Long quantidade;
    private LocalDateTime dataAtualizacao;

    public Produto(String descricaoProduto, String marca, String categoria, Long quantidade, LocalDateTime dataAtualizacao) {
        this.descricaoProduto = descricaoProduto;
        this.marca = marca;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.dataAtualizacao = dataAtualizacao;
    }
}
