package com.fiap.techchallenge.appointment_history_service.application.usecase;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;

public interface ReadAppointmentByIdCase {
    AppointmentHistoryResponse getById(String appointmentId, String authToken);
}

