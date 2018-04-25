package com.projet.ecommerce.entrypoint.graphQL.categorie;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorieQuery {

    @Autowired
    private ICategorieBusiness categorieBusiness;

    public TypeRuntimeWiring produitWiring() {
        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("getAllProduit", new DataFetcher() {
            @Override
            public List<ProduitDTO> get(DataFetchingEnvironment env) {
                System.out.println(produitBusiness.getAll());
                return produitBusiness.getAll();
            }
        });
        builder.dataFetcher("getProduitByRef", new DataFetcher() {
            @Override
            public ProduitDTO get(DataFetchingEnvironment env) {
                return produitBusiness.getByRef(env.getArgument("ref"));
            }
        });

        builder.dataFetcher("getProduitByCategorie", new DataFetcher() {
            @Override
            public List<ProduitDTO> get(DataFetchingEnvironment env) {
                return produitBusiness.getByCategorie(env.getArgument("nom"));
            }
        });
        return builder.build();
    }
}
