package com.fiap.techchallenge.appointment_history_service.application.usecase;

public interface SaveAppointmentHistoryFromEventCase {
    void handle(String routingKey, String rawJson);
}
