package com.fiap.techchallenge.appointment_history_service.application.usecase;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;

public interface UpdateAppointmentHistoryCase {
    AppointmentHistoryResponse updateById(String appointmentId, String observation, String authToken);
}

