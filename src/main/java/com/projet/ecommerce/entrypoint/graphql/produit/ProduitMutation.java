package com.projet.ecommerce.entrypoint.graphql.produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.persistance.entity.Produit;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class ProduitMutation {

    @Autowired
    private IProduitBusiness produitBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");

        builder.dataFetcher("addProduit", (DataFetchingEnvironment environment) -> {
                    double prixHT = environment.getArgument("prixHT");
                    return produitBusiness.add(environment.getArgument("ref"), environment.getArgument("nom"), environment.getArgument("description"), (float) prixHT, environment.getArgument("nouvelleCat"));
                }
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