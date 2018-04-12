package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entité représentant la table caracteristique sous forme de classe.
 */

@Entity
@Table(name="caracteristique")
public class Caracteristique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique")
    private int idCaracteristique;

    @OneToOne
    @JoinColumn(name="id_type_caracteristique")
    private TypeCaracteristique typeCaracteristique;

    @Column
    private String valeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_produit")
    private Produit produit;

    /**
     * Retourne id de la caractéristique.
     * @return l'id de la caracteristique
     */
    public int getIdCaracteristique() {
        return idCaracteristique;
    }

    /**
     * Retourne l'objet TypeCaracteristique lié à la caractéristique.
     * @return un objet de type TypeCaracteristique
     */
    public TypeCaracteristique getTypeCaracteristique() {
        return typeCaracteristique;
    }

    /**
     * Remplace l'objet TypeCaracteristique  lié à la caractéristique par l'objet TypeCaracteristique mit en paramètre.
     * @param typeCaracteristique un objet TypeCaracteristique
     */
    public void setTypeCaracteristique(TypeCaracteristique typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
    }

    /**
     * Retourne la valeur contenu dans la caractéristique.
     * @return la valeur de la caracteristique
     */
    public String getValeur() {
        return valeur;
    }

    /**
     * Remplace la valeur contenu dans la caractéristique par la valeur mit en paramètre.
     * @param valeur la nouvelle valeur de la caracteristique
     */
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
