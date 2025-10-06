package com.fiap.techchallenge.appointment_history_service.domain.model;

public record AddressDomain(String zipCode, String street, String number, String complement,
                            String neighborhood, String city, String state) {
}
