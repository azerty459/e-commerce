package com.projet.ecommerce.persistance.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
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
     * @return l'url liée à la photo.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Remplace l'url de la photo par celle-ci mit en paramètre.
     * @param url La nouvelle URL qui pointe vers la photo
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * Remplace l'id de la photo par celui-ci mit en paramètre.
     * @param idPhoto Le nouveau id de la photo
     */
    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }


    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
