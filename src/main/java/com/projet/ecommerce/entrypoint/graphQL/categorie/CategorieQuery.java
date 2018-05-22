package com.projet.ecommerce.entrypoint.graphQL.categorie;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.entrypoint.graphQL.GraphQLCustomException;
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
        builder.dataFetcher("categories", (DataFetchingEnvironment environment) ->
                categorieBusiness.getCategorie((environment.getArgument("id") != null) ? environment.getArgument("id") : 0, environment.getArgument("nom"),  environment.getFields().toString().contains("sousCategories"))
        );
        return builder.build();
    }
}
