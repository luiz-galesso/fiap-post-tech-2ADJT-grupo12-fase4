package com.fase4.techchallenge.fiap.msgestaopedidos.entity.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private long id;

    private int quantidade;

    private double valorProduto;
}
