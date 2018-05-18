package com.projet.ecommerce.entrypoint.graphQL.categorie;

import com.projet.ecommerce.business.ICategorieBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategorieQuery {

    @Autowired
    private ICategorieBusiness categorieBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("categories", (DataFetchingEnvironment environment) ->
                categorieBusiness.getCategorie(environment.getArgument("nom"), environment.getFields().toString().contains("sousCategories"),
                        environment.getFields().toString().contains("parent"))
        );
        return builder.build();
    }
}
