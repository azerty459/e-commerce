package com.projet.ecommerce.entrypoint.graphql.utilisateur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.ecommerce.business.IUtilisateurBusiness;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.persistance.entity.AuthData;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMutation {

    @Autowired
    private IUtilisateurBusiness utilisateurBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");

        builder.dataFetcher("addUtilisateur", (DataFetchingEnvironment environment) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Object rawInput = environment.getArgument("utilisateur");
                    UtilisateurDTO utilisateurDTO = mapper.convertValue(rawInput, UtilisateurDTO.class);
                    return utilisateurBusiness.add(utilisateurDTO);
                }
        );

        builder.dataFetcher("updateUtilisateur", (DataFetchingEnvironment environment) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Object rawInput = environment.getArgument("utilisateur");
                    UtilisateurDTO utilisateurDTO = mapper.convertValue(rawInput, UtilisateurDTO.class);
                    return utilisateurBusiness.update(utilisateurDTO);
                }
        );

        builder.dataFetcher("deleteUtilisateur", (DataFetchingEnvironment environment) ->
                utilisateurBusiness.delete(environment.getArgument("email"), (environment.getArgument("id") != null) ? environment.getArgument("id") : 0)
        );
        builder.dataFetcher("signinUtilisateur", (DataFetchingEnvironment environment) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Object rawInput = environment.getArgument("auth");
                    AuthData authData = mapper.convertValue(rawInput, AuthData.class);
                    try {
                        return utilisateurBusiness.signinUser(authData);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

        );
        return builder.build();

    }
}