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
 * Entité représentant la table caractéristique sous forme de classe.
 */

@Entity
@Table(name = "caracteristique")
public class Caracteristique {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique")
    private int idCaracteristique;

    @Column(name = "type_caracteristique")
    private String typeCaracteristique;

    @Column(name = "valeur_caracteristique")
    private String valeurCaracteristique;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "produit_caracteristique",
            inverseJoinColumns = {@JoinColumn(name = "reference_produit")},
            joinColumns = {@JoinColumn(name = "id_caracteristique")}
    )
    private List<Produit> produits;
    
    /**
     * Retourne l'id de la caractéristique.
     *
     * @return l'id de la caractéristique
     */
    public int getIdCaracteristique() {
        return idCaracteristique;
    }

    /**
     * Remplace l'id de la caractéristique par celui-ci mit en paramètre.
     *
     * @param idCaracteristique La nouvelle ID de la caractéristique
     */
    public void setIdCaracteristique(int idCaracteristique) {
        this.idCaracteristique = idCaracteristique;
    }
    
    /**
     * Retourne le type de la caractéristique.
     *
     * @return le type de la caractéristique
     */
    public String getTypeCaracteristique() {
        return typeCaracteristique;
    }

    /**
     * Remplace le type de la caractéristique par celui-ci mit en paramètre.
     *
     * @param typeCaracteristique Le nouveau type de la caractéristique
     */
    public void setTypeCaracteristique(String typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
    }

    /**
     * Retourne la valeur de la caractéristique.
     *
     * @return la valeur de la caractéristique
     */
    public String getValeurCaracteristique() {
        return valeurCaracteristique;
    }

    /**
     * Remplace la valeur de la caractéristique par celui-ci mit en paramètre.
     *
     * @param valeurCaracteristique La valeur de la caractéristique
     */
    public void setValeurCaracteristique(String valeurCaracteristique) {
        this.valeurCaracteristique = valeurCaracteristique;
    }
    
    /**
     * Retourne une liste de produits liés à la caractéristique.
     *
     * @return
     */
    public List<Produit> getProduits() {
        return produits;
    }
    
    /**
     * Remplace la liste produit par celle-ci mit en paramètre.
     *
     * @param produits Une liste de produit
     */
    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
