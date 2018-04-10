package com.projet.ecommerce.entrypoint;

import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Contrôleur de graphQL permettant de répondre sur l'URL "/graphql" et d'exécuter les requêtes curl demandées.
 */

@RestController
@RequestMapping("/graphql")
public class GraphQLController {

    @Autowired
    private GraphQL graphQL;

    @RequestMapping(method = RequestMethod.POST)
    public Object handle(@RequestBody Map<String,String> query) {
        return graphQL.execute(query.get("query")).getData();
    }
}
