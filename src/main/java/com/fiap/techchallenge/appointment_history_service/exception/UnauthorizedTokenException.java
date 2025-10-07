package com.fiap.techchallenge.appointment_history_service.exception;

public class UnauthorizedTokenException extends RuntimeException {
    public UnauthorizedTokenException(String message) {
        super(message);
    }
}
