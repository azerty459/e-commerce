package com.projet.ecommerce.persistance.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Entité représentant la table categorie sous forme de classe.
 */

@Entity
@Table(name = "categorie_supprime")
public class CategorieSupprime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie")
    private int idCategorie;


    @Column(name = "nom_categorie")
    private String nomCategorie;

    @Column(name = "borne_gauche")
    private int borneGauche;

    @Column(name = "borne_droit")
    private int borneDroit;

    @Column(name = "level")
    private int level;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "produit_categorie_supprime",
            inverseJoinColumns = {@JoinColumn(name = "reference_produit")},
            joinColumns = {@JoinColumn(name = "id_categorie")}
    )
    private List<Produit> produits;

    /**
     * Retourne le nom de la catégorie supprimé.
     *
     * @return le nom de la catégorie supprimé
     */
    public String getNomCategorie() {
        return nomCategorie;
    }

    /**
     * Remplace le nom de la catégorie supprimé par celui-ci mit en paramètre.
     *
     * @param nomCategorie Le nouveau nom de la catégorie supprimé
     */
    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    /**
     * Retourne l'indice de la borne gauche de la catégorie supprimé.
     *
     * @return l'indice de la borne gauche
     */
    public int getBorneGauche() {
        return borneGauche;
    }

    /**
     * Remplace l'indice de la borne gauche par celui-ci mit en paramètre.
     *
     * @param borneGauche Le nouvel indice de la borne gauche
     */
    public void setBorneGauche(int borneGauche) {
        this.borneGauche = borneGauche;
    }

    /**
     * Retourne l'indice de la borne droit de la catégorie supprimé.
     *
     * @return l'indice de la borne droit
     */
    public int getBorneDroit() {
        return borneDroit;
    }

    /**
     * Remplace l'indice de la borne droit par celui-ci mit en paramètre.
     *
     * @param borneDroit Le nouvel indice de la borne droit
     */
    public void setBorneDroit(int borneDroit) {
        this.borneDroit = borneDroit;
    }

    /**
     * Retourne une liste de produits liés à la catégorie supprimé.
     *
     * @return
     */
    public List<Produit> getProduits() {
        return produits;
    }

    /**
     * Retourne le level de la catégorie supprimé.
     *
     * @return le level de la catégorie supprimé.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Remplace le level par celui-ci mit en paramètre.
     *
     * @param level Level de la catégorie supprimé.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Remplace la liste produit par celle-ci mit en paramètre.
     *
     * @param produits Une liste de produit
     */
    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    /**
     * Retourne l'id de la catégorie supprimé.
     *
     * @return l'id de la catégorie supprimé
     */
    public int getIdCategorie() {
        return idCategorie;
    }

    /**
     * Remplace l'id de la catégorie supprimé par celui-ci mit en paramètre.
     *
     * @param idCategorie La nouvelle ID de la catégorie supprimé
     */
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
}
