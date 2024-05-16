package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.enums.SituacaoEntrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_entrega")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Entrega {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entrega_generator")
    @SequenceGenerator(name = "entrega_generator", sequenceName = "entrega_sequence", allocationSize = 1)
    private Long id;
    @NotNull
    private Long idPedido;
    @ManyToOne
    @NotNull
    private Entregador entregador;
    @NotNull
    @ManyToOne
    private TabelaDeFrete tabelaDeFrete;
    private String idRastreio;
    @NotNull
    private Endereco enderecoOrigem;
    @NotNull
    private Endereco enderecoDestino;
    @NotNull
    private LocalDateTime dataPedido;
    @NotNull
    private LocalDateTime dataRegistro;
    private LocalDateTime dataPrevisao;
    @NotNull
    private SituacaoEntrega situacaoEntrega;
    @NotNull
    private LocalDateTime dataSituacaoEntrega;
    private String descritivoLocalizacao;
    @NotNull
    private String nomeRecebedor;
}
