package com.projet.ecommerce.business.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Categorie).
 */
public class UtilisateurDTO {

    /**
     * L'id de l'utilisateur
     */
    private int id;

    /**
     * L'adresse email de l'utilisateur
     */
    private String email;

    /**
     * Le mot de passe de l'utilisateur
     */
    private String mdp;

    /**
     * Le nom de l'utilisateur
     */
    private String nom = "";

    /**
     * Le prénom de l'utilisateur
     */
    private String prenom = "";


    /**
     * Une collection de rôle associer à l'utilisateur
     */
    private Collection<RoleDTO> roles = new ArrayList<>();

    /**
     * Obtenir l'id de l'utilisateur
     *
     * @return l'id de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Définir la nouvelle id de l'utilisateur
     *
     * @param id La nouvelle id de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtenir l'adresse email de l'utilisateur
     *
     * @return l'adresse email de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définir la nouvelle email adresse de l'utilisateur
     *
     * @param email La nouvelle adresse email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtenir le mot de passe de l'utilisateur
     *
     * @return le mot de passe de l'utilisateur
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Définir le nouveau mot de passe de l'utilisateur
     *
     * @param mdp Le nouveau mot de passe
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Obtenir le nom de l'utilisateur
     *
     * @return le nom de l'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définir le nouveau nom de l'utilisateur
     *
     * @param nom Le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtenir le prénom de l'utilisateur
     *
     * @return le prénom de l'utilisateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définir le prénom de l'utilisateur
     *
     * @param prenom Le prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Obtenir la collection de rôle associé à l'utilisateur
     *
     * @return la collection de rôle associé à l'utilisateur
     */
    public Collection<RoleDTO> getRoles() {
        return roles;
    }

    /**
     * Définir la nouvelle collection de rôle à associer à l'utilisateur
     *
     * @param roles La nouvelle collection de rôle
     */
    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
