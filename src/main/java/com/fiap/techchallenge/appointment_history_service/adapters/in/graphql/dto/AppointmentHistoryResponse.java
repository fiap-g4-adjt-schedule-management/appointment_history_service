package com.fiap.techchallenge.appointment_history_service.adapters.in.graphql.dto;

import com.fiap.techchallenge.appointment_history_service.application.dto.AppointmentEventDto;

public record AppointmentHistoryResponse(
        String appointmentId,
        PersonDoctorResponse doctor,
        AppointmentHistoryResponse.PersonPatientResponse patient,
        AppointmentHistoryResponse.PersonNurseResponse nurse,
        String reason,
        String createdAt,
        String scheduledAt,
        String ingestedAt,
        String status,
        String observation
) {
    public record PersonDoctorResponse(Integer id, String name, String cpf, String email, String phone,
                                       String crm, AppointmentHistoryResponse.SpecialityResponse speciality,
                                       AppointmentHistoryResponse.AddressResponse address) {
    }


    public record PersonPatientResponse(Integer id, String name, String cpf, String email, String phone,
                                AppointmentEventDto.Address address) {
    }


    public record PersonNurseResponse(Integer id, String name, String cpf, String email, String phone,
                              String coren, AppointmentHistoryResponse.AddressResponse address) {
    }


    public record SpecialityResponse(Integer id, String name) {
    }


    public record AddressResponse(String zipCode, String street, String number, String complement,
                          String neighborhood, String city, String state) {
    }
}
