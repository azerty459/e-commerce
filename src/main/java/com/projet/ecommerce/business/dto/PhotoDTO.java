package com.projet.ecommerce.business.dto;


/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Photo).
 */

public class PhotoDTO {

    private int idPhoto;

    private String url;

    /**
     * Retourne l'id de l'objet Produit.
     *
     * @return l'id de l'objet Produit
     */
    public int getIdPhoto() {
        return idPhoto;
    }

    /**
     * Remplace l'id de l'objet Produit par celui-ci mit en paramètre.
     *
     * @param idPhoto Le nouveau ID de la photo
     */
    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    /**
     * Retourne l'url liée à la photo.
     *
     * @return l'url liée à la photo
     */
    public String getUrl() {
        return url;
    }

    /**
     * Remplace l'url de la photo par celle-ci mit en paramètre.
     *
     * @param url La nouvelle URL à prendre en compte.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
