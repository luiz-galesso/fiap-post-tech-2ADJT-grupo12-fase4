package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_tabela_frete")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TabelaDeFrete {

    @Id
    private Long id;
    @NotNull
    private String cepOrigem;
    @NotNull
    private String cepDestino;
    private Double valorFrete;
    private Long prazoEmHoras;



}
