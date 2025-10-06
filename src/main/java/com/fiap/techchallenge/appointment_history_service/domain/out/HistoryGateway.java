package com.fiap.techchallenge.appointment_history_service.domain.out;

import com.fiap.techchallenge.appointment_history_service.domain.model.AppointmentHistoryDomain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistoryGateway {
    Boolean existsByAppointmentId(Long appointmentId);

    AppointmentHistoryDomain save(AppointmentHistoryDomain history);

    Optional<AppointmentHistoryDomain> findByAppointmentId(Long appointmentId);

    Optional<AppointmentHistoryDomain> updateObservation(Long appointmentId, String observation);

    List<AppointmentHistoryDomain> findByDoctorAndDate(Long doctorId, LocalDate startDate, LocalDate endDate);

    List<AppointmentHistoryDomain> findByPatientAndDate(Long patientId, LocalDate startDate, LocalDate endDate);

}
