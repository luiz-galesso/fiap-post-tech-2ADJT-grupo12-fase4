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
public class Cliente {

    private String email;
    private String nome;
    private Endereco endereco;

}
