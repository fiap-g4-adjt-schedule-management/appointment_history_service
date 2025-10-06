package com.fiap.techchallenge.appointment_history_service.application.usecase.impl;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;
import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.mapper.DomainToResponseMapper;
import com.fiap.techchallenge.appointment_history_service.application.exception.AccessDeniedException;
import com.fiap.techchallenge.appointment_history_service.application.exception.InvalidEventPayloadException;
import com.fiap.techchallenge.appointment_history_service.application.exception.NotFoundException;
import com.fiap.techchallenge.appointment_history_service.application.usecase.UpdateAppointmentHistoryCase;
import com.fiap.techchallenge.appointment_history_service.domain.model.AppointmentHistoryDomain;
import com.fiap.techchallenge.appointment_history_service.domain.out.AuthorizationProfileClient;
import com.fiap.techchallenge.appointment_history_service.domain.out.HistoryGateway;
import com.fiap.techchallenge.appointment_history_service.domain.util.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateAppointmentHistoryCaseImpl implements UpdateAppointmentHistoryCase {
    private final HistoryGateway historyGateway;
    private final DomainToResponseMapper mapper;
    private final AuthorizationProfileClient profiles;

    @Override
    public AppointmentHistoryResponse updateById(String appointmentId, String observation, String authToken) {
        validateIsNotNull(appointmentId);
        validateIsAllowed(authToken);

        log.info("Searching for appointment by ID: {} for update", appointmentId);
        var history = updateObservation(Long.parseLong(appointmentId), observation);
        return mapper.toResponse(history);
    }

    private static void validateIsNotNull(String appointmentId) {
        if (appointmentId == null || appointmentId.isEmpty()) {
            throw new InvalidEventPayloadException("AppointmentId is required");
        }
    }

    private void validateIsAllowed(String authToken) {
        var profile = profiles.resolve(authToken);
        if (profile.role() != Role.DOCTOR) {
            log.warn("Access denied for update! userId={} with role={} ", profile.userId(), profile.role());
            throw new AccessDeniedException("Only doctors can update observation");
        }
    }

    private AppointmentHistoryDomain updateObservation(Long appointmentId, String observation) {
        return historyGateway.updateObservation(appointmentId, observation)
                .orElseThrow(() -> new NotFoundException("AppointmentId not found with ID: " + appointmentId));

    }
}
