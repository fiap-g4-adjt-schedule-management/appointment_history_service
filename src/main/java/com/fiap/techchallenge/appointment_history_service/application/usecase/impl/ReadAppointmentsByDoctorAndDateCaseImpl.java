package com.fiap.techchallenge.appointment_history_service.application.usecase.impl;

import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto.AppointmentHistoryResponse;
import com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.mapper.DomainToResponseMapper;
import com.fiap.techchallenge.appointment_history_service.exception.AccessDeniedException;
import com.fiap.techchallenge.appointment_history_service.exception.InvalidEventPayloadException;
import com.fiap.techchallenge.appointment_history_service.exception.NotFoundException;
import com.fiap.techchallenge.appointment_history_service.application.usecase.ReadAppointmentsByDoctorAndDateCase;
import com.fiap.techchallenge.appointment_history_service.domain.out.AuthorizationProfileClient;
import com.fiap.techchallenge.appointment_history_service.domain.out.AppointmentHistoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadAppointmentsByDoctorAndDateCaseImpl implements ReadAppointmentsByDoctorAndDateCase {
    private final AppointmentHistoryGateway historyGateway;
    private final DomainToResponseMapper mapper;
    private final AuthorizationProfileClient profiles;

    @Override
    public List<AppointmentHistoryResponse> getByDoctorAndDateCase(String doctorId, LocalDate start, LocalDate end, String authToken) {
        validateIsNotNull(doctorId);

        log.info("Call authentication API");
        validateIsAllowed(authToken, doctorId);

        log.info("Searching for appointment by doctor ID: {} and start date: {} and end date: {}", doctorId, start, end);
        var listAppointment =  historyGateway.findByDoctorAndDate(Long.parseLong(doctorId), start, end)
                .stream()
                .map(mapper::toResponse)
                .toList();
        if(listAppointment.isEmpty()) {
            throw new NotFoundException("No appointments found for DoctorId: " + doctorId + " between " + start + " and " + end);
        }
        return listAppointment;
    }

    private void validateIsAllowed(String authToken, String doctorId) {
        var profile = profiles.resolve(authToken);
        if (!profile.isAllowedToRead(doctorId)) {
            log.warn("Access denied for visualization!");
            throw new AccessDeniedException("Access denied");
        }
    }

    private static void validateIsNotNull(String doctorId) {
        if (doctorId == null || doctorId.isEmpty()) {
            throw new InvalidEventPayloadException("DoctorId is required");
        }
    }
}
