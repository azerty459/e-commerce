package com.projet.ecommerce.business.dto;


import java.time.LocalDateTime;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (AvisClient).
 */

public class AvisClientDTO {

    /**
     * L'id de l'avis client/
     */
    private int id;

    /**
     * Date de l'avis client
     */
    private LocalDateTime date;

    /**
     * Description de l'avis
     */
    private String description;

    /**
     * Note de l'avis
     */
    private Integer note;

    private String refProduit;

    public String getRefProduit() {
        return refProduit;
    }

    public void setRefProduit(String refProduit) {
        this.refProduit = refProduit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
}
