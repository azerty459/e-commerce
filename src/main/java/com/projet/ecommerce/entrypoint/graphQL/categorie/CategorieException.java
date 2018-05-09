package com.projet.ecommerce.entrypoint.graphQL.categorie;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorieException extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    public CategorieException(String message, String nom) {
        super(message);
        extensions.put("invalideNom", nom);

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
}