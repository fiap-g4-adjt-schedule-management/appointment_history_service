package com.fiap.techchallenge.appointment_history_service.exception;

public class PersistenceException extends RuntimeException {

    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
