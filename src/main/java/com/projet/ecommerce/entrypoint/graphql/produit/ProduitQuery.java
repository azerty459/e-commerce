package com.projet.ecommerce.entrypoint.graphql.produit;

import com.projet.ecommerce.business.IProduitBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProduitQuery {

    @Autowired
    private IProduitBusiness produitBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("produits", (DataFetchingEnvironment env) ->
                produitBusiness.getAll(env.getArgument("ref"), env.getArgument("cat")
            ));
        return builder.build();
    }
}
