package com.fiap.techchallenge.appointment_history_service.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableConfigurationProperties(AuthorizationClientProperties.class)
public class AuthorizationClientConfig {

    @Bean
    WebClient webClient(AuthorizationClientProperties props) {
        var http = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, props.timeoutMillis())
                .responseTimeout(Duration.ofMillis(props.timeoutMillis()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(props.timeoutMillis(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(props.timeoutMillis(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .baseUrl(props.baseUrl())
                .clientConnector(new ReactorClientHttpConnector(http))
                .build();
    }
}
