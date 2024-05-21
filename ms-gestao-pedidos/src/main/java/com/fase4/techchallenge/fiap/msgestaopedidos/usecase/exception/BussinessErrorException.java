package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception;

public class BussinessErrorException extends RuntimeException {
    public BussinessErrorException(String message) {
        super(message);
    }

    public BussinessErrorException() {
    }
}
