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
        builder.dataFetcher("categories", new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                return categorieBusiness.getCategorie(environment.getArgument("nom"), environment.getFields().toString().contains("sousCategories"));
            }
        });
        /*
                builder.dataFetcher("categories", (DataFetchingEnvironment env) ->
                System.out.println(env)
                categorieBusiness.getCategorie(env.getArgument("nom"))
        );
         */
        return builder.build();
    }
}
