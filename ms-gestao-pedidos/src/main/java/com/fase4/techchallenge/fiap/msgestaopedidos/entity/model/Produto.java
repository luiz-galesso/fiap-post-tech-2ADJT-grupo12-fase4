package com.fase4.techchallenge.fiap.msgestaopedidos.entity.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private long id;
    private int quantidade;

}
