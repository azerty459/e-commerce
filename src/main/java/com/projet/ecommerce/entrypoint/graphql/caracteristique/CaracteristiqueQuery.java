package com.projet.ecommerce.entrypoint.graphql.caracteristique;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaracteristiqueQuery {
    @Autowired
    private ICaracteristiqueBusiness caracteristiqueBusiness;

    public TypeRuntimeWiring produitWiring(){
        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("caracteristiques",(DataFetchingEnvironment env) ->
        caracteristiqueBusiness.getCaracteristique()
        );
        return builder.build();
    }
}
