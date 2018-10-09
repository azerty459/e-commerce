package com.projet.ecommerce.business.dto;

public class CaracteristiqueDTO {

    /**
     * L'id de la caractéristique
     */
    private int id;

    /**
     * La valeur du libellé de la caractéristique
     */
    private String label;

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
