package com.fiap.techchallenge.appointment_history_service.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AppointmentEventDto(
        @JsonProperty("consultationId")
        Long appointmentId,
        PersonDoctor doctor,
        PersonPatient patient,
        PersonNurse nurse,
        String reason,
        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        LocalDateTime dateTime,
        String status,
        String observation
) {
    public record PersonDoctor(Integer id, String name, String cpf, String email, String phone,
                               String crm, Speciality speciality, Address address) {
    }


    public record PersonPatient(Integer id, String name, String cpf, String email, String phone,
                                Address address) {
    }


    public record PersonNurse(Integer id, String name, String cpf, String email, String phone,
                              String coren, Address address) {
    }


    public record Speciality(Integer id, String name) {
    }


    public record Address(String zipCode, String street, String number, String complement,
                          String neighborhood, String city, String state) {
    }
}
