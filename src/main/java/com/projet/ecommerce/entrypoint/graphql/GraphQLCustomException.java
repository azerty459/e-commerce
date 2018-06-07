package com.projet.ecommerce.entrypoint.graphql;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe permettante de créer des érreur GraphQL personnalisée.
 */

public class GraphQLCustomException extends RuntimeException implements GraphQLError {

    private transient Map<String, Object> extensions = new HashMap<>();

    /**
     *  Créer un objet GraphQLCustomException.
     * @param message Le message de l'exception
     */
    public GraphQLCustomException(String message) {
        super(message);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    /**
     * Retourne une map de String et Objet.
     * @return une map de String et Objet.
     */
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    /**
     * Retourne l'erreur type.
     * @return l'erreur type.
     */
    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    /**
     * Permet d'ajouter une valeur dans la Map extensions.
     * @param clef Le nom de la clef à ajouter
     * @param value La valeur associé à la clef
     */
    public void ajouterExtension(String clef, String value){
        extensions.put(clef, value);
    }
}