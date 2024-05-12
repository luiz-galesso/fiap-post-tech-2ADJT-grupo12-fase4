package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception;

public class BusinessErrorException extends RuntimeException {
    public BusinessErrorException(String message) {
        super(message);
    }
    public BusinessErrorException() {

    }
}

