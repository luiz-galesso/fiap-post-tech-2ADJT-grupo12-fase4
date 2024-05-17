package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.enums.SituacaoEntrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
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
    private Long prazoEntregaEmHoras;
    @NotNull
    private Double valorFrete;
    private String idRastreio;
    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name="logradouro",column = @Column(name="origemLogradouro")),
            @AttributeOverride(name="numero",column = @Column(name="origemNumero")),
            @AttributeOverride(name="bairro",column = @Column(name="origemBairro")),
            @AttributeOverride(name="complemento",column = @Column(name="origemComplemento")),
            @AttributeOverride(name="cep",column = @Column(name="origemCep")),
            @AttributeOverride(name="cidade",column = @Column(name="origemCidade")),
            @AttributeOverride(name="estado",column = @Column(name="origemEstado")),
            @AttributeOverride(name="referencia",column = @Column(name="origemReferencia"))
    })
    private Endereco enderecoOrigem;

    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name="logradouro",column = @Column(name="destinoLogradouro")),
            @AttributeOverride(name="numero",column = @Column(name="destinoNumero")),
            @AttributeOverride(name="bairro",column = @Column(name="destinoBairro")),
            @AttributeOverride(name="complemento",column = @Column(name="destinoComplemento")),
            @AttributeOverride(name="cep",column = @Column(name="destinoCep")),
            @AttributeOverride(name="cidade",column = @Column(name="destinoCidade")),
            @AttributeOverride(name="estado",column = @Column(name="destinoEstado")),
            @AttributeOverride(name="referencia",column = @Column(name="destinoReferencia"))
    })
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
