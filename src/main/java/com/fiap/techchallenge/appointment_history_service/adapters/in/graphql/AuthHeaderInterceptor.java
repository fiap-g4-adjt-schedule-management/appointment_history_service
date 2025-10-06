package com.fiap.techchallenge.appointment_history_service.adapters.in.graphql;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthHeaderInterceptor implements WebGraphQlInterceptor {
    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        String token = request.getHeaders().getFirst("Authorization");
        request.configureExecutionInput((executionInput, builder) ->
                builder.graphQLContext(ctx -> ctx.put("authToken", token)).build());
        return chain.next(request);
    }
}
