package com.fase4.techchallenge.fiap.msgestaopedidos.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @NotNull(message = "Email do Cliente deve ser Preenchido.")
    private String email;
    private String nome;
    private LocalDateTime dataRegistro;
    private LocalDate dataNascimento;

}
