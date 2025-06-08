package com.example.employeemanagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

/**
 * Custom exception handler for GraphQL errors.
 * Converts various exceptions into appropriate GraphQL error responses.
 */
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
    private static final Logger log = LoggerFactory.getLogger(GraphQLExceptionHandler.class);

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        log.error("Error executing query", ex);
        
        if (ex instanceof InvalidCredentialsException) {
            return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(Map.of(
                    "code", "INVALID_CREDENTIALS",
                    "classification", "AuthenticationError"
                ))
                .build();
        }

        if (ex instanceof UsernameNotFoundException) {
            return GraphqlErrorBuilder.newError()
                .message("Invalid username or password")
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(Map.of(
                    "code", "INVALID_CREDENTIALS",
                    "classification", "AuthenticationError"
                ))
                .build();
        }

        if (ex instanceof AuthenticationException) {
            return GraphqlErrorBuilder.newError()
                .message("Invalid username or password")
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(Map.of(
                    "code", "INVALID_CREDENTIALS",
                    "classification", "AuthenticationError"
                ))
                .build();
        }

        if (ex instanceof ResourceNotFoundException) {
            Map<String, Object> extensions = new HashMap<>();
            extensions.put("code", "NOT_FOUND");
            
            return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(extensions)
                .build();
        }

        if (ex instanceof AccessDeniedException) {
            Map<String, Object> extensions = new HashMap<>();
            extensions.put("code", "FORBIDDEN");
            
            return GraphqlErrorBuilder.newError()
                .message("Access denied")
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(extensions)
                .build();
        }
       
        return GraphqlErrorBuilder.newError()
            .message(ex.getMessage())
            .path(env.getExecutionStepInfo().getPath())
            .location(env.getField().getSourceLocation())
            .build();
    }
}