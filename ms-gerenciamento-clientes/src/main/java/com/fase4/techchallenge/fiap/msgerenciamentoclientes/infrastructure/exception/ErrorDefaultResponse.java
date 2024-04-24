package com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDefaultResponse {
    private Instant timestamp;
    private String status;
    private  String message;
}
