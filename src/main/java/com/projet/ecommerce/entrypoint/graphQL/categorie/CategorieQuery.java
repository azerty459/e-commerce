package com.projet.ecommerce.entrypoint.graphQL.categorie;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
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
        builder.dataFetcher("categories", new DataFetcher() {
            @Override
            public List<CategorieDTO> get(DataFetchingEnvironment env) {
              return categorieBusiness.getCategorie(env.getArgument("nom"));
            }
        });
        return builder.build();
    }
}
