package com.fiap.techchallenge.appointment_history_service.domain.model;

public record NurseDomain(Integer id, String name, String cpf, String email, String phone,
                          String coren, AddressDomain address){
}
