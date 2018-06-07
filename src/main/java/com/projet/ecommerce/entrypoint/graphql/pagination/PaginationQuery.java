package com.projet.ecommerce.entrypoint.graphql.pagination;

import com.projet.ecommerce.business.IPaginationBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaginationQuery {

    @Autowired
    private IPaginationBusiness paginationBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");

        builder.dataFetcher("pagination", (DataFetchingEnvironment env) ->
                paginationBusiness.getPagination(env.getArgument("type"), env.getArgument("page"), env.getArgument("npp"))
                );
        return builder.build();
    }
}
