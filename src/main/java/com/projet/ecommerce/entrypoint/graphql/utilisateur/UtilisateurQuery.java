package com.projet.ecommerce.entrypoint.graphql.utilisateur;

import com.projet.ecommerce.business.IUtilisateurBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurQuery {

    @Autowired
    private IUtilisateurBusiness utilisateurBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("utilisateurs", (DataFetchingEnvironment env) ->
                utilisateurBusiness.getUtilisateur((env.getArgument("id") != null) ? env.getArgument("id") : 0, env.getArgument("email"), env.getArgument("nom"), env.getArgument("prenom"), env.getArgument("role"))
        );

        builder.dataFetcher("login", (DataFetchingEnvironment env) ->
                utilisateurBusiness.login(env.getArgument("email"), env.getArgument("mdp"))
        );
        return builder.build();
    }
}
