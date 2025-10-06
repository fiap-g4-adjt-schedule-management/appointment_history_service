package com.fiap.techchallenge.appointment_history_service.application.usecase;

public interface SaveHistoryFromEventCase {
    void handle(String routingKey, String rawJson);
}
