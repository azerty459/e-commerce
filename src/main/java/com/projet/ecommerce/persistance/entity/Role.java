package com.projet.ecommerce.persistance.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entité représentant la table rôle sous forme de classe.
 */

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "role", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Utilisateur> utilisateurs;


    /**
     * Retourne l'id du rôle.
     *
     * @return l'id du role
     */
    public int getId() {
        return id;
    }

    /**
     * Remplace l'id du rôle par le nouveau id mit en paramètre.
     *
     * @param id La nouvelle id du rôle
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom du rôle.
     *
     * @return le nom du rôle
     */
    public String getNom() {
        return nom;
    }

    /**
     * Remplace le nom du rôle par le nouveau nom mit en paramètre.
     *
     * @param nom Le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne une liste d'utilisateurs qui est associés au rôle.
     *
     * @return une liste d'utilisateurs qui est associés au rôle
     */
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    /**
     * Remplace la liste d'utilisateurs par celle-ci mit en paramètre.
     *
     * @param utilisateurs Une nouvelle liste d'utilisateurs à associer au rôle
     */
    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}

    




    