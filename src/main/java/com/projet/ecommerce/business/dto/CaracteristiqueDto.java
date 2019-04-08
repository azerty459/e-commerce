package com.projet.ecommerce.business.dto;

public class CaracteristiqueDto {
    
    private int id;
    
    private String label;
    
    public CaracteristiqueDto(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
