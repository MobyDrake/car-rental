package com.mobydrake.inventory.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import static org.springframework.graphql.execution.ErrorType.INTERNAL_ERROR;
import static org.springframework.graphql.execution.ErrorType.NOT_FOUND;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

    private static final String DEFAULT_MESSAGE = "Internal exception";

    @Override
    protected GraphQLError resolveToSingleError(Throwable exception, DataFetchingEnvironment env) {
        final String message = exception.getMessage() != null ? exception.getMessage() : DEFAULT_MESSAGE;
        final ErrorType errorType = switch (exception) {
            case EntityNotFound _ -> NOT_FOUND;
            default -> INTERNAL_ERROR;
        };
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(message)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
