package com.projet.ecommerce.persistance.entity;

public class SigninPayload {
    private final String token;
    private final Utilisateur utilisateur;

    public SigninPayload(String token, Utilisateur user) {
        this.token = token;
        utilisateur = user;
    }

    public String getToken() {
        return token;
    }

    public Utilisateur getUser() {
        return utilisateur;
    }
}
