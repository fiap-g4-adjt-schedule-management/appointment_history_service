package com.fiap.techchallenge.appointment_history_service.application.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.appointment_history_service.application.dto.AppointmentEventDto;
import com.fiap.techchallenge.appointment_history_service.application.exception.InvalidEventPayloadException;
import com.fiap.techchallenge.appointment_history_service.application.exception.PersistenceException;
import com.fiap.techchallenge.appointment_history_service.application.mapper.EventToDomainMapper;
import com.fiap.techchallenge.appointment_history_service.application.usecase.SaveHistoryFromEventCase;
import com.fiap.techchallenge.appointment_history_service.domain.model.AppointmentHistoryDomain;
import com.fiap.techchallenge.appointment_history_service.domain.out.HistoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveHistoryFromEventCaseImpl implements SaveHistoryFromEventCase {

    private final HistoryGateway repo;
    private final EventToDomainMapper mapper;
    private final ObjectMapper json;

    @Override
    public void handle(String routingKey, String rawJson) {
        AppointmentEventDto dto = deserializeAppointmentEvent(rawJson);

        try {
            if (Boolean.TRUE.equals(repo.existsByAppointmentId(dto.appointmentId()))) {
                log.info("There is already a appointment saved in the database with this ID={}", dto.appointmentId());
                return;
            }
            AppointmentHistoryDomain domain = mapper.toDomain(dto);
            repo.save(domain);
            log.info("Saved appointmentId={} status={}", domain.getAppointmentId(), domain.getStatus());
        } catch (DataAccessException e) {
            log.error("Error trying to save to database id={}", dto.appointmentId());
            throw new PersistenceException(e.getMessage(), e);
        } catch (RuntimeException ex) {
        log.warn("invalid payload on mapping appointmentId={} err={}",
                dto.appointmentId(), ex.getMessage());
        throw new InvalidEventPayloadException("Invalid payload content", ex);
    }

}

    private AppointmentEventDto deserializeAppointmentEvent(String rawJson) {
        try {
            return json.readValue(rawJson, AppointmentEventDto.class);
        } catch (JsonProcessingException e) {
            log.warn("invalid json err={}", e.getOriginalMessage());
            throw new InvalidEventPayloadException("Invalid JSON", e);
        }
    }
}
