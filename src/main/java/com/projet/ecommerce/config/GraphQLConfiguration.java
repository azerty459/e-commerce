package com.projet.ecommerce.config;

import com.projet.ecommerce.graphQL.Mutation;
import com.projet.ecommerce.graphQL.Query;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.coxautodev.graphql.tools.SchemaParser.newParser;

/**
 * Configuration de graphQL.
 */

@Configuration
public class GraphQLConfiguration {

    @Autowired
    private Query query;

    @Autowired
    private Mutation mutation;

    @Bean
    public GraphQL graphQL() {
        return GraphQL.newGraphQL(graphQLSchema())
                .build();
    }

    @Bean
    public GraphQLSchema graphQLSchema() {
        return newParser()
                .file("graphql/schema.graphqls")
                .resolvers(query, mutation)
                .build()
                .makeExecutableSchema();
    }
}