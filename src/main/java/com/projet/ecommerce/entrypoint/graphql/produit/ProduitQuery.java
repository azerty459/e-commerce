package com.projet.ecommerce.entrypoint.graphql.produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projet.ecommerce.business.IProduitBusiness;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class ProduitQuery {

    @Autowired
    private IProduitBusiness produitBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("produits", (DataFetchingEnvironment env) -> {
            if (env.getArgument("ref") != null) {
                return produitBusiness.getByRef("ref");
            } else {
                return produitBusiness.getAll(env.getArgument("ref"), env.getArgument("cat"), env.getArgument("nom"));
            }
        });
        return builder.build();
    }
}
