package com.fiap.techchallenge.appointment_history_service.exception;

public class InvalidEventPayloadException extends RuntimeException {

    public InvalidEventPayloadException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidEventPayloadException(String message) {
        super(message);
    }
}
