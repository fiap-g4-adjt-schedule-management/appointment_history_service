package com.fiap.techchallenge.appointment_history_service.domain.out;

import com.fiap.techchallenge.appointment_history_service.domain.auth.AuthorizationProfileDomain;
import com.fiap.techchallenge.appointment_history_service.exception.AuthBadResponseException;

public interface AuthorizationProfileClient {
    AuthorizationProfileDomain resolve(String authToken) throws AuthBadResponseException;
}
