package com.fiap.techchallenge.appointment_history_service.application.dto;

import com.fiap.techchallenge.appointment_history_service.domain.util.Role;

public record AuthorizationProfile(String userId,
                                   Role role) {}
