package com.projet.ecommerce.business.dto;

public class CaracteristiqueDto {
    
    private int id;
    
    private String libelle;
    
    public CaracteristiqueDto(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String label) {
        this.libelle = label;
    }
    
}
