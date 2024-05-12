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

    private String status;

    private String meioPagamento;

    private double valorPedido;

    private Cliente cliente;

    @ElementCollection
    private List<Produto> produtos;

    private LocalDateTime dataPagamento;

    private LocalDateTime dataEntrega;

    private LocalDateTime dataCriacao;

    public Pedido(Cliente cliente, List<Produto> produtos, double valorPedido, String status, String meioPagamento, LocalDateTime dataCriacao) {
        this.cliente = cliente;
        this.produtos = produtos;
        this.valorPedido = valorPedido;
        this.status = status;
        this.meioPagamento = meioPagamento;
        this.dataCriacao = dataCriacao;
    }

}
