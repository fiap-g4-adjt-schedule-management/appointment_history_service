package com.fiap.techchallenge.appointment_history_service.application.exception;

public class PersistenceException extends RuntimeException {

    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
