package com.projet.ecommerce.entrypoint;

import com.projet.ecommerce.entrypoint.graphQL.GraphQlUtility;
import graphql.ExceptionWhileDataFetching;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur de graphQL permettant de répondre sur l'URL "/graphql" et d'exécuter les requêtes curl demandées.
 */

@RestController
public class GraphQLController {

    private GraphQL graphQL;

    @Autowired
    private GraphQlUtility graphQlUtility;

    GraphQLController(GraphQlUtility graphQlUtility) throws IOException {
        graphQL = graphQlUtility.createGraphQlObject();
    }

    @RequestMapping(value = "/graphql", method = RequestMethod.POST)
    public Object handle(@RequestBody Map<String,String> query) {
        ExecutionResult result = graphQL.execute(query.get("query"));
        if(result.getErrors().isEmpty()){
            return graphQL.execute(query.get("query")).getData();
        }else{
            List<GraphQLError> graphQLErrors = graphQL.execute(query.get("query")).getErrors();
            return graphQlUtility.graphQLErrorHandler(graphQLErrors);
        }
    }
}