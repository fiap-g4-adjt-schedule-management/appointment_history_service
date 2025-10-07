package com.fiap.techchallenge.appointment_history_service.adapters.out.mongo;

import com.fiap.techchallenge.appointment_history_service.adapters.out.mongo.mapper.DocumentMapper;
import com.fiap.techchallenge.appointment_history_service.adapters.out.mongo.repository.AppointmentHistoryRepository;
import com.fiap.techchallenge.appointment_history_service.domain.model.AppointmentHistoryDomain;
import com.fiap.techchallenge.appointment_history_service.domain.out.AppointmentHistoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AppointmentHistoryGatewayImpl implements AppointmentHistoryGateway {

    private final AppointmentHistoryRepository repository;
    private final DocumentMapper mapper;

    @Override
    public Boolean existsByAppointmentId(Long id) {
        return repository.existsById(id);
    }

    @Override
    public AppointmentHistoryDomain save(AppointmentHistoryDomain history) {
        var saved = repository.save(mapper.toDocument(history));
        return mapper.toAppointmentHistoryDomain(saved);
    }

    @Override
    public Optional<AppointmentHistoryDomain> findByAppointmentId(Long id) {
        return repository.findById(id).map(mapper::toAppointmentHistoryDomain);
    }

    @Override
    public Optional<AppointmentHistoryDomain> updateObservation(Long appointmentId, String observation) {
        return repository.findById(appointmentId)
                .map(document -> {
                    document.setObservation(observation);
                    var saved = repository.save(document);
                    return mapper.toAppointmentHistoryDomain(saved);
                });
    }

    @Override
    public List<AppointmentHistoryDomain> findByDoctorAndDate(Long doctorId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();
        return repository.findByDoctor_IdAndScheduledAtBetween(doctorId, start, end)
                .stream()
                .map(mapper::toAppointmentHistoryDomain)
                .toList();
    }

    @Override
    public List<AppointmentHistoryDomain> findByPatientAndDate(Long patientId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();
        return repository.findByPatient_IdAndScheduledAtBetween(patientId, start, end)
                .stream()
                .map(mapper::toAppointmentHistoryDomain)
                .toList();
    }
}
