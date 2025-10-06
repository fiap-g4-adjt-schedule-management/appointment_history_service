package com.fiap.techchallenge.appointment_history_service.adapters.out.mongo.repository;

import com.fiap.techchallenge.appointment_history_service.adapters.out.mongo.document.AppointmentHistoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentHistoryRepository extends MongoRepository<AppointmentHistoryDocument, Long> {

    List<AppointmentHistoryDocument> findByDoctor_IdAndScheduledAtBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
    List<AppointmentHistoryDocument> findByPatient_IdAndScheduledAtBetween(Long patientId, LocalDateTime start, LocalDateTime end);
}
