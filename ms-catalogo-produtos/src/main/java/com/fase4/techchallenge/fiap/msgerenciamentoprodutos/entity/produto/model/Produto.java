package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_produto")
public class Produto {

    @Id
    private String codProduto;
    @NotNull
    private String descricaoProduto;
    @NotNull
    private String categoria;
    @NotNull
    private Long quantidade;
    @NotNull
    private LocalDateTime dataAtualizacao;

}
