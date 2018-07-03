package com.projet.ecommerce.persistance.authentification;

public class SigninPayload {
    private final Token token;

    public SigninPayload(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

}
