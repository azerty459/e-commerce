package com.projet.ecommerce.entrypoint.graphql.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projet.ecommerce.business.IPaginationBusiness;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class PaginationQuery {

    @Autowired
    private IPaginationBusiness paginationBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");

        builder.dataFetcher("pagination", (DataFetchingEnvironment env) ->
                paginationBusiness.getPagination(env.getArgument("type"), env.getArgument("page"), env.getArgument("npp"),
                        env.getArgument("nom"), (env.getArgument("categorie") != null) ? env.getArgument("categorie") : 0)
        );
        return builder.build();
    }
}
