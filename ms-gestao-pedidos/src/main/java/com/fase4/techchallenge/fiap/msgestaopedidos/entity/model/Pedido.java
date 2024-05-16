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

    private Integer idEnderecoCliente;

    @ElementCollection
    private List<Produto> produtos;

    @NotNull(message = "Deve ser informado o Valor do Pedido.")
    private double valorPedido;

    private double valorFrete;

    private String status;

    @NotNull(message = "Deve ser informado o Meio de Pagamento.")
    private String meioPagamento;

    private LocalDateTime dataPagamento;

    private LocalDateTime dataEntrega;

    private LocalDateTime dataCriacao;

    public Pedido(String emailCliente, Integer idEnderecoCliente, List<Produto> produtos, double valorPedido, String status, String meioPagamento, LocalDateTime dataCriacao) {
        this.emailCliente = emailCliente;
        this.idEnderecoCliente = idEnderecoCliente;
        this.produtos = produtos;
        this.valorPedido = valorPedido;
        this.status = status;
        this.meioPagamento = meioPagamento;
        this.dataCriacao = dataCriacao;
    }

}
