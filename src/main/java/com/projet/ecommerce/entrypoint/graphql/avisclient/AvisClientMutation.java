package com.projet.ecommerce.entrypoint.graphql.avisclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.ecommerce.business.IAvisClientBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvisClientMutation {

    @Autowired
    private IAvisClientBusiness avisClientBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");

        builder.dataFetcher("addAvisClient", (DataFetchingEnvironment environment) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Object rawInput = environment.getArgument("avis");
                    AvisClientDTO avisClient = mapper.convertValue(rawInput, AvisClientDTO.class);
                    return avisClientBusiness.add(avisClient);
                }
        );

        builder.dataFetcher("updateAvisClient", (DataFetchingEnvironment environment) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Object rawInput = environment.getArgument("avis");
                    AvisClientDTO avisClient = mapper.convertValue(rawInput, AvisClientDTO.class);
                    return avisClientBusiness.update(avisClient);
                }
        );

        builder.dataFetcher("deleteAvisClient", (DataFetchingEnvironment environment) ->
                avisClientBusiness.delete(environment.getArgument("id"))
        );

        return builder.build();

    }
}