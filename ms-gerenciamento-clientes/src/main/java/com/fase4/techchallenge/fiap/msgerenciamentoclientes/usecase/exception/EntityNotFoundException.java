package com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {

    }
}

