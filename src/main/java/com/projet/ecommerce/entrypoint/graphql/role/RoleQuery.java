package com.projet.ecommerce.entrypoint.graphql.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projet.ecommerce.business.IRoleBusiness;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class RoleQuery {

    @Autowired
    private IRoleBusiness roleBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("roles", (DataFetchingEnvironment env) ->
                roleBusiness.getRole((env.getArgument("id") != null) ? env.getArgument("id") : 0, env.getArgument("nom"))
        );
        return builder.build();
    }
}
