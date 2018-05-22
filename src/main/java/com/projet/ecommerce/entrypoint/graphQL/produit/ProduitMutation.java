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

        builder.dataFetcher("addProduit", (DataFetchingEnvironment environment) ->
                produitBusiness.add(environment.getArgument("ref"), environment.getArgument("nom"), environment.getArgument("description"), environment.getArgument("prixHT"), environment.getArgument("nouvelleCat"))
        );
        builder.dataFetcher("updateProduit", (DataFetchingEnvironment environment) ->
                produitBusiness.update(environment.getArgument("ref"), environment.getArgument("nom"), environment.getArgument("description"), environment.getArgument("prixHT"), (environment.getArgument("nouvelleCat") != null) ? environment.getArgument("nouvelleCat") : 0, (environment.getArgument("supprimerCat") != null) ? environment.getArgument("supprimerCat") : 0)
        );

        builder.dataFetcher("deleteProduit", (DataFetchingEnvironment environment) ->
                produitBusiness.delete(environment.getArgument("ref"))
        );
        return builder.build();

    }
}