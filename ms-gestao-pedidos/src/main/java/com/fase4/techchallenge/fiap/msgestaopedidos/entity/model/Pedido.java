package com.fase4.techchallenge.fiap.msgestaopedidos.entity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_generator")
    @SequenceGenerator(name = "pedido_generator", sequenceName = "pedido_sequence", allocationSize = 1)
    private Long idPedido;

    private String emailCliente;

    private Endereco endereco;

    @ElementCollection
    private List<Produto> produtos;

    @NotNull(message = "Deve ser informado o Valor do Pedido.")
    private double valorPedido;

    private double valorFrete;

    private String status;

    @NotNull(message = "Deve ser informado o Meio de Pagamento.")
    private String meioPagamento;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataPagamento;

    private LocalDateTime dataEntrega;

    private Long idTabelaFrete;

    public Pedido(String emailCliente, Endereco endereco, List<Produto> produtos, double valorPedido, double valorFrete, String status, String meioPagamento, LocalDateTime dataCriacao, Long idTabelaFrete) {
        this.emailCliente = emailCliente;
        this.endereco = endereco;
        this.produtos = produtos;
        this.valorPedido = valorPedido;
        this.valorFrete = valorFrete;
        this.status = status;
        this.meioPagamento = meioPagamento;
        this.dataCriacao = dataCriacao;
        this.idTabelaFrete = idTabelaFrete;
    }

}
