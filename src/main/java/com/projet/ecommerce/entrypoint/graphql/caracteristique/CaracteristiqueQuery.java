package com.projet.ecommerce.entrypoint.graphql.caracteristique;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.impl.CaracteristiqueBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaracteristiqueQuery {

    @Autowired
    private CaracteristiqueBusiness caracteristiqueBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");

        builder.dataFetcher("caracteristiques", (DataFetchingEnvironment environment) -> {
            return caracteristiqueBusiness.getAll();
        });


        return builder.build();
    }
}