package com.projet.ecommerce.business.dto;

import java.util.Objects;

public class TypeCaracteristiqueDTO {

    private Short id;

    private String nom;

    public TypeCaracteristiqueDTO() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeCaracteristiqueDTO that = (TypeCaracteristiqueDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
