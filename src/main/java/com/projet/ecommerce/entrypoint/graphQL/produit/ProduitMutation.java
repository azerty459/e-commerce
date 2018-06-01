package com.projet.ecommerce.entrypoint.graphQL.produit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.persistance.entity.Produit;
import graphql.TypeResolutionEnvironment;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

@Component
public class ProduitMutation {

    @Autowired
    private IProduitBusiness produitBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");

        builder.dataFetcher("addProduit", (DataFetchingEnvironment environment) ->
                produitBusiness.add(environment.getArgument("ref"), environment.getArgument("nom"), environment.getArgument("description"), environment.getArgument("prixHT"), environment.getArgument("nouvelleCat"))
        );

        builder.dataFetcher("updateProduit", (DataFetchingEnvironment environment) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Object rawInput = environment.getArgument("produit");
                    Produit produit = mapper.convertValue(rawInput, Produit.class);
                    return produitBusiness.update(produit);
                }
        );

        builder.dataFetcher("deleteProduit", (DataFetchingEnvironment environment) ->
                produitBusiness.delete(environment.getArgument("ref"))
        );
        return builder.build();

    }
}