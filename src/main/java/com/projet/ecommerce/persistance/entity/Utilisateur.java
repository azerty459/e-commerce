package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entité représentant la table utilisateur sous forme de classe.
 */

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private int id;

    @NotNull
    @Column(name = "adresse_email", unique = true)
    private String email;

    @NotNull
    @Column(name = "mot_de_passe")
    private String mdp;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role")
    private Role role;

    /**
     * Retourne l'id de l'utilisateur
     *
     * @return l'id de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Remplace l'id de l'utilisateur par le nouveau id mit en paramètre.
     *
     * @param id La nouvelle id de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne l'adresse email de l'utilisateur
     *
     * @return l'adresse email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Remplace l'email de l'utilisateur par la nouvelle email mit en paramètre.
     *
     * @param email La nouvelle adresse email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe de l'utilisateur
     *
     * @return le mot de passe de l'utilisateur
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Remplace le mot de passe de l'utilisateur par le nouveau mot de passe mit en paramètre.
     *
     * @param mdp Le nouveau mot de passe
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Retourne une liste de rôles associé à l'utilisateur
     *
     * @return une liste de rôle associé à l'utilisateur
     */
    public Role getRole() {
        return role;
    }

    /**
     * Remplace la liste de rôles par celle-ci mit en paramètre.
     *
     * @param role Une nouvelle liste de rôle à associer à l'utilisateur
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Retourne le nom de l'utilisateur.
     *
     * @return le nom de l'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Remplace le nom de l'utilisateur par celui-mit en paramètre
     *
     * @param nom Le nouveau nom de l'utilisateur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le prénom de l'utilisateur.
     *
     * @return le prénom de l'utilisateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Remplace le prénom de l'utilisateur par celui-mit en paramètre.
     *
     * @param prenom Le nouveau prénom de l'utilisateur
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
