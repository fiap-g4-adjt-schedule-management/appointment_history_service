package com.fiap.techchallenge.appointment_history_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "auth.client")
public record AuthorizationClientProperties(
        String baseUrl,
        String path,
        Integer timeoutMillis
) {}
