package com.fiap.techchallenge.appointment_history_service.domain.out;

import com.fiap.techchallenge.appointment_history_service.application.dto.AuthorizationProfile;

public interface AuthorizationProfileClient {
    AuthorizationProfile resolve(String authToken);
}
