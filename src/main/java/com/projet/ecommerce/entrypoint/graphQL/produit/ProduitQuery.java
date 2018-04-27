package com.projet.ecommerce.entrypoint.graphQL.produit;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Produit;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProduitQuery {

    @Autowired
    private IProduitBusiness produitBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("produits", (DataFetchingEnvironment env) -> {
                return produitBusiness.getAll(env.getArgument("ref"), env.getArgument("cat"));
            });

//        builder.dataFetcher("produits", new DataFetcher() {
//
//            @Override
//            public List<ProduitDTO> get(DataFetchingEnvironment env) {
//                return produitBusiness.getAll(env.getArgument("ref"), env.getArgument("cat"));
//            }
//
//        });
        return builder.build();
    }
}
