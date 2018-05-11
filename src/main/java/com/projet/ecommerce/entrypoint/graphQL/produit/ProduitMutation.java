package com.projet.ecommerce.entrypoint.graphQL.produit;

import com.projet.ecommerce.business.IProduitBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProduitMutation {

    @Autowired
    private IProduitBusiness produitBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");

        builder.dataFetcher("addProduit", (DataFetchingEnvironment env) ->
                produitBusiness.add(env.getArgument("ref"), env.getArgument("nom"), env.getArgument("description"), env.getArgument("prixHT"), env.getArgument("nouvelleCat"))
        );
        builder.dataFetcher("updateProduit", (DataFetchingEnvironment env) ->
                produitBusiness.update(env.getArgument("ref"), env.getArgument("nom"), env.getArgument("description"), env.getArgument("prixHT"), env.getArgument("nouvelleCat"), env.getArgument("supprimerCat"))
        );

        builder.dataFetcher("deleteProduit", (DataFetchingEnvironment env) ->
                produitBusiness.delete(env.getArgument("ref"))
        );
        return builder.build();

    }
}