package com.fiap.techchallenge.appointment_history_service.adapters.in.graphql;

import com.fiap.techchallenge.appointment_history_service.application.exception.AccessDeniedException;
import com.fiap.techchallenge.appointment_history_service.application.exception.InvalidEventPayloadException;
import com.fiap.techchallenge.appointment_history_service.application.exception.NotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;


import java.util.Map;

@ControllerAdvice
public class GraphQlExceptionAdvice {

    @GraphQlExceptionHandler(NotFoundException.class)
    public GraphQLError handleNotFound(NotFoundException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.NOT_FOUND)
                .message(ex.getMessage())
                .extensions(Map.of("code","APPOINTMENT_NOT_FOUND"))
                .build();
    }

    @GraphQlExceptionHandler(AccessDeniedException.class)
    public GraphQLError handleForbidden(AccessDeniedException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.UNAUTHORIZED)
                .message(ex.getMessage())
                .extensions(Map.of("code","UNAUTHORIZED"))
                .build();
    }

    @GraphQlExceptionHandler(InvalidEventPayloadException.class)
    public GraphQLError handleBadRequest(InvalidEventPayloadException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .extensions(Map.of("code","BAD_REQUEST"))
                .build();
    }

    @GraphQlExceptionHandler(Exception.class)
    public GraphQLError handleGeneric(Exception ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.INTERNAL_ERROR)
                .message("Unexpected error")
                .build();
    }
}
