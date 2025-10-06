package com.fiap.techchallenge.appointment_history_service.domain.model;

public record PatientDomain(Integer id, String name, String cpf, String email, String phone,
                            AddressDomain address) {
}
