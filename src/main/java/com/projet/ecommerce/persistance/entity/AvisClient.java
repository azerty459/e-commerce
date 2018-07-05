package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité représentant la table avis_client sous forme de classe.
 */

@Entity
@Table(name = "avis_client")
public class AvisClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avis")
    private int idAvis;

    /**
     * Date de l'avis du client
     */
    @Column(name="date_avis", updatable = false, insertable = false)
    private LocalDateTime date;

    /**
     * Description de l'avis du client
     */
    @Column
    private String description;

    /**
     * Note de l'avis du client
     */
    @Column
    private Integer note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_produit")
    private Produit produit;


    public int getIdAvis() {
        return idAvis;
    }

    public LocalDateTime getDateAvis() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNote() {
        return note;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public void setDateAvis(LocalDateTime date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Produit getProduit() {
        return produit;
    }
}
