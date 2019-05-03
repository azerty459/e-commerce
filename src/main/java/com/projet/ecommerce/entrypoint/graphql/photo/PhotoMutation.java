package com.projet.ecommerce.entrypoint.graphql.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projet.ecommerce.business.IPhotoBusiness;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class PhotoMutation {

    @Autowired
    private IPhotoBusiness photoBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");
        builder.dataFetcher("deletePhoto", (DataFetchingEnvironment environment) -> {
                    int idPhoto = environment.getArgument("id");
                    return photoBusiness.remove(idPhoto);
                }
        );
        return builder.build();
    }
}
