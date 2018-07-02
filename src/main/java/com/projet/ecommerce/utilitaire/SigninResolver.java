package com.projet.ecommerce.utilitaire;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.projet.ecommerce.persistance.entity.SigninPayload;
import com.projet.ecommerce.persistance.entity.Utilisateur;

public class SigninResolver implements GraphQLResolver<SigninPayload> {

    public Utilisateur utilisateur(SigninPayload payload) {
        return payload.getUser();
    }
}