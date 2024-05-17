package com.fase4.techchallenge.fiap.msgestaopedidos.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
@Data
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

    private double valorPedido;

    private double valorFrete;

    private String status;

    private String meioPagamento;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataPagamento;

    private LocalDateTime dataEntrega;

    public Pedido(String emailCliente, Endereco endereco, List<Produto> produtos, double valorPedido, String status, String meioPagamento, LocalDateTime dataCriacao) {
        this.emailCliente = emailCliente;
        this.endereco = endereco;
        this.produtos = produtos;
        this.valorPedido = valorPedido;
        this.status = status;
        this.meioPagamento = meioPagamento;
        this.dataCriacao = dataCriacao;
    }

}
