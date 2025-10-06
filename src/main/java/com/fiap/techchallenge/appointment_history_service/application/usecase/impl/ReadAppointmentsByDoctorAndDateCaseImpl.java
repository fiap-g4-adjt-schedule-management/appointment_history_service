package com.fiap.techchallenge.appointment_history_service.application.usecase.impl;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;
import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.mapper.DomainToResponseMapper;
import com.fiap.techchallenge.appointment_history_service.application.dto.AuthorizationProfile;
import com.fiap.techchallenge.appointment_history_service.application.exception.AccessDeniedException;
import com.fiap.techchallenge.appointment_history_service.application.exception.InvalidEventPayloadException;
import com.fiap.techchallenge.appointment_history_service.application.exception.NotFoundException;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentHistoryCase;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentsByDoctorAndDateCase;
import com.fiap.techchallenge.appointment_history_service.domain.model.AppointmentHistoryDomain;
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
public class ReadAppointmentsByDoctorAndDateCaseImpl implements ReadAppointmentsByDoctorAndDateCase {
    private final HistoryGateway historyGateway;
    private final DomainToResponseMapper mapper;
    private final AuthorizationProfileClient profiles;

    @Override
    public List<AppointmentHistoryResponse> getByDoctorAndDateCase(String doctorId, LocalDate start, LocalDate end, String authToken) {
        validateIsNotNull(doctorId);
        validateIsAllowed(authToken);

        log.info("Searching for appointment by doctor ID: {} and start date: {} and end date: {}", doctorId, start, end);
        return historyGateway.findByDoctorAndDate(Long.parseLong(doctorId), start, end)
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

    private static void validateIsNotNull(String doctorId) {
        if (doctorId == null || doctorId.isEmpty()) {
            throw new InvalidEventPayloadException("DoctorId is required");
        }
    }
}
