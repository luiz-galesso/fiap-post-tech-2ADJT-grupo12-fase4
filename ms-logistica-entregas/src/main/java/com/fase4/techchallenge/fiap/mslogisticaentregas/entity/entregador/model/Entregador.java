package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="tb_entregador")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Entregador {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entregador_generator")
    @SequenceGenerator(name = "entregador_generator", sequenceName = "entregador_sequence", allocationSize = 1)
    private Long id;
    @NotNull
    private String nome;
    private Long cnpj;
    @NotNull
    private String situacao;
    @NotNull
    private Long quantidadeRecursosDisponiveis;

    public Entregador(String nome, Long cnpj, String situacao, Long quantidadeRecursosDisponiveis) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.situacao = situacao;
        this.quantidadeRecursosDisponiveis = quantidadeRecursosDisponiveis;
    }

}
