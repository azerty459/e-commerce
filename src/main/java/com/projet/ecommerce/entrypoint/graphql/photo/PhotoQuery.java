package com.projet.ecommerce.entrypoint.graphql.photo;

import com.projet.ecommerce.business.IPhotoBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoQuery {

    @Autowired
    private IPhotoBusiness photoBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("photos", (DataFetchingEnvironment env) ->
                photoBusiness.getAll(env.getArgument("ref"))
            );
        return builder.build();
    }
}
