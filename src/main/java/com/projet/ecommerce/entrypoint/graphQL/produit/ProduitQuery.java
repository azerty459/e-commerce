package com.projet.ecommerce.entrypoint.graphQL.produit;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProduitQuery {

    @Autowired
    private IProduitBusiness produitBusiness;

    public TypeRuntimeWiring produitWiring() {
        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("produits", new DataFetcher() {
            @Override
            public List<ProduitDTO> get(DataFetchingEnvironment env) {
                return produitBusiness.getAll(env.getArgument("ref"), env.getArgument("cat"));
            }
        });
        return builder.build();
    }
}
