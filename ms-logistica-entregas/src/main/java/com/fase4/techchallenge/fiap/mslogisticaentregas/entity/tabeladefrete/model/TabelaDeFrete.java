package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model;

import jakarta.persistence.*;
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
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tabelaDeFrete_generator")
    @SequenceGenerator(name = "tabelaDeFrete_generator", sequenceName = "tabelaDeFrete_sequence", allocationSize = 1)
    private Long id;
    @NotNull
    private String cepOrigem;
    @NotNull
    private String cepDestino;
    private Double valorFrete;
    private Long prazoEntregaEmHoras;

    public TabelaDeFrete(String cepOrigem, String cepDestino, Double valorFrete, Long prazoEntregaEmHoras) {
        this.cepOrigem = cepOrigem;
        this.cepDestino = cepDestino;
        this.valorFrete = valorFrete;
        this.prazoEntregaEmHoras = prazoEntregaEmHoras;
    }
}
