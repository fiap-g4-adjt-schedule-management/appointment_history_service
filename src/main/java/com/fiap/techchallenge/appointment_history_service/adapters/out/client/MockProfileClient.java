package com.fiap.techchallenge.appointment_history_service.adapters.out.client;

import com.fiap.techchallenge.appointment_history_service.application.dto.AuthorizationProfile;
import com.fiap.techchallenge.appointment_history_service.domain.out.AuthorizationProfileClient;
import com.fiap.techchallenge.appointment_history_service.domain.util.Role;
import org.springframework.stereotype.Component;

@Component
public class MockProfileClient implements AuthorizationProfileClient {

    @Override
    public AuthorizationProfile resolve(String authToken) {
        if (authToken == null || authToken.isBlank()) {
            throw new IllegalArgumentException("Missing Authorization header");
        }

        String raw = authToken.replaceFirst("(?i)^Bearer\\s+", "").trim();
        String[] parts = raw.split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Bad token format");
        }

        Role role = Role.valueOf(parts[0].toUpperCase());
        String userId = parts[1];
        return new AuthorizationProfile(userId, role);
    }
}
