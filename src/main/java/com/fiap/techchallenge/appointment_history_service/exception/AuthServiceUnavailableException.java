package com.fiap.techchallenge.appointment_history_service.exception;

public class AuthServiceUnavailableException extends RuntimeException {
    public AuthServiceUnavailableException(String message) {
        super(message);
    }
}