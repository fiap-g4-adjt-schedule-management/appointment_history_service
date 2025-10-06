package com.fiap.techchallenge.appointment_history_service.application.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
