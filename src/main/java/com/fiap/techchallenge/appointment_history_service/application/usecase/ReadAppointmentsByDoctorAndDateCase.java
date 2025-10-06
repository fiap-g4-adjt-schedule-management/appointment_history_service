package com.fiap.techchallenge.appointment_history_service.application.usecase;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReadAppointmentsByDoctorAndDateCase {
    List<AppointmentHistoryResponse> getByDoctorAndDateCase(String doctorId, LocalDate start, LocalDate end, String authToken);
}

