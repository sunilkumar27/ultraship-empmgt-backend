package com.example.employeemanagement.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Configuration
public class GraphQLConfig {
    
    /*@Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
            .scalar(ExtendedScalars.Date)
            .scalar(ExtendedScalars.DateTime);
    }*/
	
	@Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        GraphQLScalarType dateTimeScalar = GraphQLScalarType.newScalar()
            .name("DateTime")
            .description("DateTime scalar")
            .coercing(new Coercing<LocalDateTime, String>() {
                @Override
                public String serialize(Object dataFetcherResult) {
                    if (dataFetcherResult instanceof LocalDateTime) {
                        return ((LocalDateTime) dataFetcherResult).toString();
                    }
                    throw new CoercingSerializeException("Expected LocalDateTime object.");
                }

                @Override
                public LocalDateTime parseValue(Object input) {
                    try {
                        if (input instanceof String) {
                            return LocalDateTime.parse((String) input);
                        }
                    } catch (DateTimeParseException e) {
                        throw new CoercingParseValueException(
                            "Expected valid LocalDateTime string but got: " + input, e
                        );
                    }
                    throw new CoercingParseValueException("Expected LocalDateTime input.");
                }

                @Override
                public LocalDateTime parseLiteral(Object input) {
                    if (input instanceof StringValue) {
                        try {
                            return LocalDateTime.parse(((StringValue) input).getValue());
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseLiteralException(e);
                        }
                    }
                    throw new CoercingParseLiteralException("Expected StringValue.");
                }
            }).build();

        return wiringBuilder -> wiringBuilder
            .scalar(ExtendedScalars.Date)
            .scalar(dateTimeScalar);
    }

    @Bean
    public GraphQLScalarType dateScalar() {
        return ExtendedScalars.Date;
    }

    @Bean
    public GraphQLScalarType dateTimeScalar() {
        return ExtendedScalars.DateTime;
    }
}