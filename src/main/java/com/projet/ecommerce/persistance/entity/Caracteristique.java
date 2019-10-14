package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entité représentant la table caractéristique sous forme de classe.
 */

@Entity
@Table(name = "caracteristique")
@IdClass(CaracteristiqueId.class)
public class Caracteristique {
	
    @Id
    @Column(name = "reference_produit")
    private String referenceProduit;

	@Id
    @Column(name = "type_caracteristique")
    private String typeCaracteristique;

    @Column(name = "valeur_caracteristique")
    private String valeurCaracteristique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_produit", insertable= false, updatable = false)
    private Produit produit;


    /**
     * Retourne le reference produit de la caractéristique.
     *
     * @return le reference produit de la caractéristique
     */
    public String getReferenceProduit() {
        return referenceProduit;
    }

    /**
     * Remplace le referenceProduit de la caractéristique par celui-ci mit en paramètre.
     *
     * @param referenceProduit Le nouveau referenceProduit de la caractéristique
     */
    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
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
     * Retourne un produit lié à la caractéristique.
     *
     * @return
     */
    public Produit getProduit() {
        return produit;
    }
    
    /**
     * Remplace le produit par celui-ci mit en paramètre.
     *
     * @param produit un produit
     */
    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
