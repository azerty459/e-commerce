package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Entité représentant la table categorie sous forme de classe.
 */

@Entity
@Table(name="categorie")
public class Categorie {

    @Id
    @Column(name = "nom_categorie")
    private String nomCategorie;

    @Column(name = "borne_gauche")
    private int borneGauche;

    @Column(name = "borne_droit")
    private int borneDroit;

    @Column(name = "level")
    private int level;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Produit> produits;

    /**
     * Retourne le nom de la catégorie.
     * @return le nom de la catégorie
     */
    public String getNomCategorie() {
        return nomCategorie;
    }

    /**
     * Remplace le nom de la catégorie par celui-ci mit en paramètre.
     * @param nomCategorie Le nouveau nom de la catégorie
     */
    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    /**
     * Retourne l'indice de la borne gauche de la catégorie.
     * @return l'indice de la borne gauche
     */
    public int getBorneGauche() {
        return borneGauche;
    }

    /**
     * Remplace l'indice de la borne gauche par celui-ci mit en paramètre.
     * @param borneGauche Le nouvel indice de la borne gauche
     */
    public void setBorneGauche(int borneGauche) {
        this.borneGauche = borneGauche;
    }

    /**
     * Retourne l'indice de la borne droit de la catégorie.
     * @return l'indice de la borne droit
     */
    public int getBorneDroit() {
        return borneDroit;
    }

    /**
     * Remplace l'indice de la borne droit par celui-ci mit en paramètre.
     * @param borneDroit Le nouvel indice de la borne droit
     */
    public void setBorneDroit(int borneDroit) {
        this.borneDroit = borneDroit;
    }

    /**
     * Retourne une liste de produits liés à la catégorie.
     * @return
     */
    public List<Produit> getProduits() {
        return produits;
    }

    /**
     * Retourne le level de la catégorie.
     * @return le level de la catégorie.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Remplace le level par celui-ci mit en paramètre.
     * @param level Level de la catégorie.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Remplace la liste produit par celle-ci mit en paramètre.
     * @param produits Une liste de produit
     */
    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
