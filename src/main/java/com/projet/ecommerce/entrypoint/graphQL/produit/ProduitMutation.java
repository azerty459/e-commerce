package com.projet.ecommerce.entrypoint.graphQL.produit;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import graphql.schema.DataFetcher;
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

        builder.dataFetcher("addProduit", new DataFetcher() {

            @Override
            public ProduitDTO get(DataFetchingEnvironment env) {

                return produitBusiness.add(env.getArgument("ref"), env.getArgument("nom"), env.getArgument("description"), env.getArgument("prixHT"));
            }
        });

        builder.dataFetcher("updateProduit", new DataFetcher() {

            @Override
            public ProduitDTO get(DataFetchingEnvironment env) {

                return produitBusiness.update(env.getArgument("ref"), env.getArgument("nom"), env.getArgument("description"), env.getArgument("prixHT"));
            }

        });

        builder.dataFetcher("deleteProduit", new DataFetcher() {
            @Override
            public Boolean get(DataFetchingEnvironment env) {
                return produitBusiness.delete(env.getArgument("ref"));
            }
        });
        return builder.build();

    }
}