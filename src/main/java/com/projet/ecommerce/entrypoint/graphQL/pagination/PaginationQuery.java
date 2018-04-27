package com.projet.ecommerce.entrypoint.graphQL.pagination;

import com.projet.ecommerce.business.IPaginationBusiness;
import com.projet.ecommerce.business.dto.PaginationDTO;
import graphql.language.Selection;
import graphql.schema.DataFetcher;
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

        builder.dataFetcher("pagination", new DataFetcher() {
            @Override
            public PaginationDTO get(DataFetchingEnvironment env) {
                return paginationBusiness.getPagination(env.getArgument("type"), env.getArgument("page"), env.getArgument("npp"), env.getFields().get(0).getSelectionSet().getSelections().get(2).toString().contains("sousCategories"));
            }

        });
        return builder.build();
    }
}
