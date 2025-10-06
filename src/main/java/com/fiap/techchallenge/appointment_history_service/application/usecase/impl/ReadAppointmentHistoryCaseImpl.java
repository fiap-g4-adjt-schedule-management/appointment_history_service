package com.fiap.techchallenge.appointment_history_service.application.usecase.impl;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;
import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.mapper.DomainToResponseMapper;
import com.fiap.techchallenge.appointment_history_service.application.dto.AuthorizationProfile;
import com.fiap.techchallenge.appointment_history_service.application.exception.AccessDeniedException;
import com.fiap.techchallenge.appointment_history_service.application.exception.InvalidEventPayloadException;
import com.fiap.techchallenge.appointment_history_service.application.exception.NotFoundException;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentHistoryCase;
import com.fiap.techchallenge.appointment_history_service.domain.model.AppointmentHistoryDomain;
import com.fiap.techchallenge.appointment_history_service.domain.out.HistoryGateway;
import com.fiap.techchallenge.appointment_history_service.domain.out.AuthorizationProfileClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadAppointmentHistoryCaseImpl implements ReadAppointmentHistoryCase {
    private final HistoryGateway historyGateway;
    private final DomainToResponseMapper mapper;
    private final AuthorizationProfileClient profiles;

    @Override
    public AppointmentHistoryResponse getById(String appointmentId, String authToken) {
        if (appointmentId == null || appointmentId.isEmpty()) {
            throw new InvalidEventPayloadException("AppointmentId is required");
        }
        var profile = profiles.resolve(authToken);

        log.info("Searching for appointment by ID: {}", appointmentId);
        var history = findAppointmentHistoryById(Long.parseLong(appointmentId));

        if (!isAllowed(profile, history)) {
            log.warn("Access denied for userId={} with role={} to appointmentId={}", profile.userId(), profile.role(), appointmentId);
            throw new AccessDeniedException("Access denied");
        }

        return mapper.toResponse(history);
    }

    private AppointmentHistoryDomain findAppointmentHistoryById(Long appointmentId) {
        return historyGateway.findByAppointmentId(appointmentId).orElseThrow(
                () -> new NotFoundException("AppointmentId not found with ID: " + appointmentId));
    }

    private boolean isAllowed(AuthorizationProfile authProfile, AppointmentHistoryDomain h) {
        return switch (authProfile.role()) {
            case PATIENT -> String.valueOf(h.getPatient().id()).equals(authProfile.userId());
            case DOCTOR -> String.valueOf(h.getDoctor().id()).equals(authProfile.userId());
            case NURSE -> String.valueOf(h.getNurse().id()).equals(authProfile.userId());
        };
    }
}
