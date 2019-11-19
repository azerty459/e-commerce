package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {

    @Id
    @Column()
    private Short id;

    @Column(unique = true)
    @NotNull
    @Size(max = 50)
    private String nom;

    public TypeCaracteristique() {

    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
