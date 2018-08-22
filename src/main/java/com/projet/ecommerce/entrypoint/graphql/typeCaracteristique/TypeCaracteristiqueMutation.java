package com.projet.ecommerce.entrypoint.graphql.typeCaracteristique;

import com.projet.ecommerce.business.ICaracteristiqueTypeBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeCaracteristiqueMutation {
    @Autowired
    private ICaracteristiqueTypeBusiness caracteristiqueTypeBusiness;

    public TypeRuntimeWiring produitWiring(){

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");

        builder.dataFetcher("addCaracteristiqueType",(DataFetchingEnvironment env)->{

                return caracteristiqueTypeBusiness.addTypeCaracteristique(env.getArgument("type"));
                });

        builder.dataFetcher("updateCaracteristiqueType",(DataFetchingEnvironment env)->{

             return caracteristiqueTypeBusiness.updateTypeCaracteristique(env.getArgument("id"),env.getArgument("type"));
        });

        builder.dataFetcher("deleteCaracteristiqueType",(DataFetchingEnvironment env)-> {
            return caracteristiqueTypeBusiness.deleteTypeCaracteristique(env.getArgument("id"));

        });

        return builder.build();
    }
}
