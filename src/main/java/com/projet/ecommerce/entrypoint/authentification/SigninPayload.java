package com.projet.ecommerce.entrypoint.authentification;

public class SigninPayload {
    private final Token token;

    public SigninPayload(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

}
