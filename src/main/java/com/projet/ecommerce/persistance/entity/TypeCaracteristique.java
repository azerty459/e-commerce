package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entité représentant la table type_caracteristique sous forme de classe.
 */

@Entity
@Table(name = "Type_Caracteristique")
public class TypeCaracteristique {
   
	@Id
    @Column(name = "type")
    private String type;

    /**
     * Retourne le type de la caractéristique.
     *
     * @return le type de la caractéristique
     */
    public String getType() {
        return type;
    }

    /**
     * Remplace le type de la caractéristique par celui-ci mit en paramètre.
     *
     * @param typeCaracteristique Le nouveau type de la caractéristique
     */
    public void setType(String type) {
        this.type = type;
    }
}
