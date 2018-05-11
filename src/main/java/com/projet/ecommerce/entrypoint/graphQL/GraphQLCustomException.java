package com.projet.ecommerce.entrypoint.graphQL;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphQLCustomException extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    public GraphQLCustomException(String message) {
        super(message);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    public void ajouterExtansion(String clef, String nom){
        extensions.put(clef, nom);
    }
}