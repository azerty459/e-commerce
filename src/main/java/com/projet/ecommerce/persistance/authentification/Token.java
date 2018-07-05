package com.projet.ecommerce.persistance.authentification;

import com.projet.ecommerce.persistance.entity.Utilisateur;

import java.util.Date;


public class Token {

    private String token;
    private Utilisateur utilisateur;
    private String id;
    private String subject;
    private String issuer;
    private Date Expiration;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getExpiration() {
        return Expiration;
    }

    public void setExpiration(Date expiration) {
        Expiration = expiration;
    }

    public void setToken(String token) {
        this.token = token;
    }
}



