package com.projet.ecommerce.entrypoint.graphql.avisclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projet.ecommerce.business.IAvisClientBusiness;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class AvisClientQuery {

    @Autowired
    private IAvisClientBusiness avisClientBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("avisClient", (DataFetchingEnvironment env) ->
                avisClientBusiness.getAll(env.getArgument("ref"))
        );
        return builder.build();
    }
}
