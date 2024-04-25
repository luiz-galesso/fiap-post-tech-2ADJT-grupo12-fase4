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
    private Long id;

    private String emailCliente;

    @ElementCollection
    private List<Produto> produtos;

    private Endereco endereco;

    private double valorPedido;

    private String status;

    private String meioPagamento;

    private LocalDateTime dataCriacao;

    public Pedido(String emailCliente, List<Produto> produtos, Endereco endereco, double valorPedido, String status, String meioPagamento, LocalDateTime dataCriacao) {
        this.emailCliente = emailCliente;
        this.produtos = produtos;
        this.endereco = endereco;
        this.valorPedido = valorPedido;
        this.status = status;
        this.meioPagamento = meioPagamento;
        this.dataCriacao = dataCriacao;
    }
}
