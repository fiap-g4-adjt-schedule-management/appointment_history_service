package com.fiap.techchallenge.appointment_history_service.exception;

public class AuthBadResponseException extends RuntimeException{
    public AuthBadResponseException(String message) {
        super(message);
    }
}
