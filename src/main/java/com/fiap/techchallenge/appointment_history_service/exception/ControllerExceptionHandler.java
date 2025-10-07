package com.fiap.techchallenge.appointment_history_service.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;


import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler(NotFoundException.class)
    public GraphQLError handleNotFound(NotFoundException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.NOT_FOUND)
                .message(ex.getMessage())
                .extensions(Map.of("code","APPOINTMENT_NOT_FOUND"))
                .build();
    }

    @org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler(AccessDeniedException.class)
    public GraphQLError handleForbidden(AccessDeniedException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.UNAUTHORIZED)
                .message(ex.getMessage())
                .extensions(Map.of("code","UNAUTHORIZED"))
                .build();
    }

    @org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler(InvalidEventPayloadException.class)
    public GraphQLError handleBadRequest(InvalidEventPayloadException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .extensions(Map.of("code","BAD_REQUEST"))
                .build();
    }

    @org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler(AuthBadResponseException.class)
    public GraphQLError handleAuthBadResponse(AuthBadResponseException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.BAD_REQUEST)
                .message("BAD_RESPONSE_FROM_AUTH_SERVER")
                .build();
    }

    @org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler(AuthServiceUnavailableException.class)
    public GraphQLError handleAuthServiceUnavailable(AuthServiceUnavailableException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.INTERNAL_ERROR)
                .message("Unexpected error in auth server")
                .build();
    }

    @org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler(UnauthorizedTokenException.class)
    public GraphQLError handleAuthUnauthorizedToken(UnauthorizedTokenException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.UNAUTHORIZED)
                .message("UNAUTHORIZED_TOKEN")
                .build();
    }

    @org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler(Exception.class)
    public GraphQLError handleGeneric(Exception ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.INTERNAL_ERROR)
                .message("Unexpected error")
                .build();
    }
}
