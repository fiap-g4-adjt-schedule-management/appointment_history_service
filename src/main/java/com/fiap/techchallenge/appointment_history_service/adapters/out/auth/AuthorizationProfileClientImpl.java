package com.fiap.techchallenge.appointment_history_service.adapters.out.auth;

import com.fiap.techchallenge.appointment_history_service.config.AuthorizationClientProperties;
import com.fiap.techchallenge.appointment_history_service.domain.auth.AuthorizationProfileDomain;
import com.fiap.techchallenge.appointment_history_service.domain.out.AuthorizationProfileClient;
import com.fiap.techchallenge.appointment_history_service.domain.util.Role;
import com.fiap.techchallenge.appointment_history_service.exception.AuthBadResponseException;
import com.fiap.techchallenge.appointment_history_service.exception.AuthServiceUnavailableException;
import com.fiap.techchallenge.appointment_history_service.exception.UnauthorizedTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationProfileClientImpl implements AuthorizationProfileClient {

    private final WebClient authWebClient;
    private final AuthorizationClientProperties properties;

    @Override
    public AuthorizationProfileDomain resolve(String authToken) throws AuthBadResponseException {
        if (authToken == null || authToken.isBlank()) {
            throw new IllegalArgumentException("Missing Authorization header");
        }

        TokenValidatorResponse body = authWebClient.get()
                .uri(properties.path())
                .header("Authorization", authToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, resp ->
                        resp.bodyToMono(String.class).map(msg ->
                                new UnauthorizedTokenException("Token invalid/unauthorized: " + msg)))
                .onStatus(HttpStatusCode::is5xxServerError, resp ->
                        resp.bodyToMono(String.class).map(msg ->
                                new AuthServiceUnavailableException("Auth server error: " + msg)))
                .bodyToMono(TokenValidatorResponse.class)
                .block(Duration.ofMillis(properties.timeoutMillis()));

        if (body == null) {
            throw new AuthBadResponseException("Empty response from token validator");
        }

        Role role = Role.valueOf(body.userType().toUpperCase(Locale.ROOT));
        String userId = String.valueOf(body.userId());
        return new AuthorizationProfileDomain(userId, role);
    }
}
