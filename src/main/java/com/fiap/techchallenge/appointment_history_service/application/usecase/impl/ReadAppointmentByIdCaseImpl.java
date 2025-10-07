package com.fiap.techchallenge.appointment_history_service.application.usecase.impl;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;
import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.mapper.DomainToResponseMapper;
import com.fiap.techchallenge.appointment_history_service.exception.AccessDeniedException;
import com.fiap.techchallenge.appointment_history_service.exception.InvalidEventPayloadException;
import com.fiap.techchallenge.appointment_history_service.exception.NotFoundException;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentByIdCase;
import com.fiap.techchallenge.appointment_history_service.domain.model.AppointmentHistoryDomain;
import com.fiap.techchallenge.appointment_history_service.domain.out.AppointmentHistoryGateway;
import com.fiap.techchallenge.appointment_history_service.domain.out.AuthorizationProfileClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadAppointmentByIdCaseImpl implements ReadAppointmentByIdCase {
    private final AppointmentHistoryGateway historyGateway;
    private final DomainToResponseMapper mapper;
    private final AuthorizationProfileClient profiles;

    @Override
    public AppointmentHistoryResponse getById(String appointmentId, String authToken) {
        if (appointmentId == null || appointmentId.isEmpty()) {
            throw new InvalidEventPayloadException("AppointmentId is required");
        }

        log.info("Searching for appointment by ID: {}", appointmentId);
        var history = findAppointmentHistoryById(Long.parseLong(appointmentId));

        log.info("Call authentication API");
        validateIsAllowed(authToken, history);

        return mapper.toResponse(history);
    }

    private AppointmentHistoryDomain findAppointmentHistoryById(Long appointmentId) {
        return historyGateway.findByAppointmentId(appointmentId).orElseThrow(
                () -> new NotFoundException("AppointmentId not found with ID: " + appointmentId));
    }

    private void validateIsAllowed(String authToken, AppointmentHistoryDomain appointmentHistoryDomain) {
        var profile = profiles.resolve(authToken);
        if (!profile.isAllowedToRead(appointmentHistoryDomain.getPatient().id().toString())) {
            log.warn("Access denied for visualization!");
            throw new AccessDeniedException("Access denied");
        }
    }
}
