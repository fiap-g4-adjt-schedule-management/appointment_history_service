package com.fiap.techchallenge.appointment_history_service.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AppointmentHistoryDomain {

    private Long appointmentId;
    private String status;
    private String reason;
    private Instant createdAt;
    private Instant scheduledAt;
    private Instant ingestedAt;
    private String observation;

    private DoctorDomain doctor;
    private PatientDomain patient;
    private NurseDomain nurse;
}
