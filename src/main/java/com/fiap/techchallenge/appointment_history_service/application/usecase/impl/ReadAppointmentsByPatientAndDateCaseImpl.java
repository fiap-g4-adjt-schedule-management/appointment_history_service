package com.fiap.techchallenge.appointment_history_service.application.usecase.impl;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;
import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.mapper.DomainToResponseMapper;
import com.fiap.techchallenge.appointment_history_service.application.exception.AccessDeniedException;
import com.fiap.techchallenge.appointment_history_service.application.exception.InvalidEventPayloadException;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentsByPatientAndDateCase;
import com.fiap.techchallenge.appointment_history_service.domain.out.AuthorizationProfileClient;
import com.fiap.techchallenge.appointment_history_service.domain.out.HistoryGateway;
import com.fiap.techchallenge.appointment_history_service.domain.util.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadAppointmentsByPatientAndDateCaseImpl implements ReadAppointmentsByPatientAndDateCase {
    private final HistoryGateway historyGateway;
    private final DomainToResponseMapper mapper;
    private final AuthorizationProfileClient profiles;

    @Override
    public List<AppointmentHistoryResponse> getByPatientAndDateCase(String patientId, LocalDate start, LocalDate end, String authToken) {
        validateIsNotNull(patientId);
        validateIsAllowed(authToken);

        log.info("Searching for appointment by patient ID: {} and start date: {} and end date: {}", patientId, start, end);
        return historyGateway.findByPatientAndDate(Long.parseLong(patientId), start, end)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validateIsAllowed(String authToken) {
        var profile = profiles.resolve(authToken);
        if (profile.role() == Role.PATIENT) {
            log.warn("Access denied for visualization! userId={} with role={} ", profile.userId(), profile.role());
            throw new AccessDeniedException("Access denied");
        }
    }

    private static void validateIsNotNull(String patientId) {
        if (patientId == null || patientId.isEmpty()) {
            throw new InvalidEventPayloadException("Patient Id is required");
        }
    }
}
