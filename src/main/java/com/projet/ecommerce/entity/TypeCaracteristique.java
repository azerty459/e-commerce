package com.projet.ecommerce.entity;

import javax.persistence.*;

/**
 * Entité représentant la table type_caracteristique sous forme de classe.
 */

@Entity
@Table(name="type_caracteristique")
public class TypeCaracteristique {
    @Id
    @Column(name = "id_type_caracteristique")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTypeCaracteristique;

    @Column
    private String type;

    /**
     * Retourne l'id de TypeCaracteristique.
     * @return l'id de type caractéristique
     */
    public int getIdTypeCaracteristique() {
        return idTypeCaracteristique;
    }


    /**
     * Retourne le type de la caracteristique.
     * @return le type de la caracteristique
     */
    public String getType() {
        return type;
    }

    /**
     * Modifie le type de la caracteristique.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
