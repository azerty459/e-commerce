package com.projet.ecommerce.entrypoint.graphql.caracteristique;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaracteristiqueMutation {
    @Autowired
    private ICaracteristiqueBusiness caracteristiqueBusiness;
    public TypeRuntimeWiring produitWiring(){
        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");

        builder.dataFetcher("addCaracteristique",(DataFetchingEnvironment env)-> {
            TypeCaracteristique typeCaracteristique = env.getArgument("typeCaracteristique");
        return caracteristiqueBusiness.addCaracteristique(typeCaracteristique,env.getArgument("valeur"));
        });

        builder.dataFetcher("updateCaracteristique",(DataFetchingEnvironment env)->{
            return caracteristiqueBusiness.updateCaracteristique(env.getArgument("id"),env.getArgument("typeCaracterisique"),env.getArgument("valeur"));
        });

        builder.dataFetcher("deleteCaracteristique",(DataFetchingEnvironment env)->{
            return  caracteristiqueBusiness.deleteCaracteristique(env.getArgument("id"));
        });


        return builder.build();

    }

}
