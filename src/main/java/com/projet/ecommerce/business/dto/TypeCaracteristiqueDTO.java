package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur .
 */
public class TypeCaracteristiqueDTO {
private  int id_type_caracteristique ;
private String libelle ;



    public int getId_type_caracteristique() {
        return id_type_caracteristique;
    }

    public void setId_type_caracteristique(int id_type_caracteristique) {
        this.id_type_caracteristique = id_type_caracteristique;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public TypeCaracteristiqueDTO(int id_type_caracteristique, String libelle) {
        this.id_type_caracteristique = id_type_caracteristique;
        this.libelle = libelle;
    }

    public TypeCaracteristiqueDTO() {
    }

    @Override
    public String toString() {
        return "TypeCaracteristiqueDTO{" +
                "id_type_caracteristique=" + id_type_caracteristique +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
