package com.fiap.techchallenge.appointment_history_service.adapters.out.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "appointment_history")
@CompoundIndex(name = "patient_scheduled_idx", def = "{'patient.id': 1, 'scheduledAt': -1}")
public class AppointmentHistoryDocument {

    @Id
    private Long appointmentId;

    private String status;
    private String reason;
    private String observation;

    private Instant createdAt;
    private Instant scheduledAt;
    private Instant ingestedAt;

    private DoctorDocument doctor;
    private PatientDocument patient;
    private NurseDocument nurse;
}
