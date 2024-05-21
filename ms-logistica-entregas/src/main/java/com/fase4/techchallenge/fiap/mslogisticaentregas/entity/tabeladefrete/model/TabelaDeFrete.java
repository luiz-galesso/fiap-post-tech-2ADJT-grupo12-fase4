package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tabelafrete_generator")
    @SequenceGenerator(name = "tabelafrete_generator", sequenceName = "tabelafrete_sequence", allocationSize = 1)
    private Long id;
    @NotNull
    private Long cepOrigem;
    @NotNull
    private Long cepDestinoInicial;
    @NotNull
    private Long cepDestinoFinal;
    private Double valorFrete;
    private Long prazoEntregaEmHoras;

    @ManyToOne
    @NotNull
    private Entregador entregador;

    public TabelaDeFrete(Long cepOrigem, Long cepDestinoInicial, Long cepDestinoFinal,Double valorFrete, Long prazoEntregaEmHoras, Entregador entregador) {
        this.cepOrigem = cepOrigem;
        this.cepDestinoInicial = cepDestinoInicial;
        this.cepDestinoFinal = cepDestinoFinal;
        this.valorFrete = valorFrete;
        this.prazoEntregaEmHoras = prazoEntregaEmHoras;
        this.entregador = entregador;
    }
}
