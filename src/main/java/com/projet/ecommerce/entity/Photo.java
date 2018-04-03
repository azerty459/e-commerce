package com.projet.ecommerce.entity;

import javax.persistence.*;

/**
 * Entité représentant la table photo sous forme de classe.
 */

@Entity
@Table(name="photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo")
    private int idPhoto;

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "reference_produit")
    private Produit produit;

    /**
     * Retourne l'id de la photo.
     * @return l'id de la photo.
     */
    public int getIdPhoto() {
        return idPhoto;
    }

    /**
     * Retourne l'url liée à la photo.
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * Remplace l'url de la photo par celle mit en paramètre.
     * @param url Une URL qui pointe vers la photo
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Retourne le produit lié à la photo.
     * @return le produit lié à la photo
     */
    public Produit getProduit() {
        return produit;
    }

    /**
     * Remplace le produit lié à la photo par celui-ci mit en paramètre.
     * @param produit Un objet de type Produit
     */
    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
