package com.fiap.techchallenge.appointment_history_service.domain.model;

import com.fiap.techchallenge.appointment_history_service.domain.util.Role;

public record HistoryRequest(String userId, Role role) {
}
