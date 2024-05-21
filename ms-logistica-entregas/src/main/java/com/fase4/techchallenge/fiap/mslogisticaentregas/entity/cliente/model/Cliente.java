package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private String email;
    private String nome;
    private LocalDateTime dataRegistro;
    private LocalDate dataNascimento;

}
