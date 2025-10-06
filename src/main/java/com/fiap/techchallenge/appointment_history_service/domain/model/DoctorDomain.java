package com.fiap.techchallenge.appointment_history_service.domain.model;

public record DoctorDomain(Integer id, String name, String cpf, String email, String phone,
                           String crm, SpecialityDomain speciality, AddressDomain address){
}
