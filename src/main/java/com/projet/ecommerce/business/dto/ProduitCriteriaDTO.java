package com.projet.ecommerce.business.dto;

public class ProduitCriteriaDTO {

    private Double noteMin;
    private Double noteMax;
    private String nom;
    private String nomLike;
    private String nomCategorie;

    public ProduitCriteriaDTO() {
    }

    public Double getNoteMin() {
        return noteMin;
    }

    public void setNoteMin(Double noteMin) {
        this.noteMin = noteMin;
    }

    public Double getNoteMax() {
        return noteMax;
    }

    public void setNoteMax(Double noteMax) {
        this.noteMax = noteMax;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomLike() {
        return nomLike;
    }

    public void setNomLike(String nomLike) {
        this.nomLike = nomLike;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
}
