package com.projet.ecommerce.entrypoint.graphql.typeCaracteristique;

import com.projet.ecommerce.business.ICaracteristiqueTypeBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeCaracteristiqueQuery {
    @Autowired
    private ICaracteristiqueTypeBusiness caracteristiqueTypeBusiness;

    public TypeRuntimeWiring produitWiring(){

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("caracteristiqueType",(DataFetchingEnvironment env)->
            caracteristiqueTypeBusiness.getTypeCaracteristique());
        return builder.build();
    }
}
