package com.mahuru.springserver.config;

import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MapScalarConfiguration {

  @Bean
  public GraphQLScalarType mapScalar() {
    return GraphQLScalarType.newScalar()
        .name("Map")
        .description("Java 8 Map as scalar.")
        .coercing(new Coercing<Map<String, Object>, Map<String, Object>>() {

          @Override
          public Map<String, Object> serialize(Object dataFetcherResult) throws CoercingSerializeException {
            return ((Map<String, Object>) dataFetcherResult);
          }

          @Override
          public Map<String, Object> parseValue(Object input) throws CoercingParseValueException {
            return ((Map<String, Object>) input);
          }

          @Override
          public Map<String, Object> parseLiteral(Object input) throws CoercingParseLiteralException {
            return ((Map<String, Object>) input);
          }
        })
        .build();
  }
}
