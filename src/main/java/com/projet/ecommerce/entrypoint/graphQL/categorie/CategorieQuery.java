package com.projet.ecommerce.entrypoint.graphQL.categorie;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
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
        builder.dataFetcher("categories", (DataFetchingEnvironment env) ->
                categorieBusiness.getCategorie(env.getArgument("nom")

                ));
        return builder.build();
    }
}
