package com.fiap.techchallenge.appointment_history_service.adapters.out.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenValidatorResponse(
        String userType,
        @JsonProperty("id")
        Long userId
) {}