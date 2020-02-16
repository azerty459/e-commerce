package com.projet.ecommerce.business.dto;


/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Photo).
 */

public class PhotoDTO {

    /**
     * L'id de la photo dans la base de données/
     */
    private int id;

    /**
     * L'URL de la photo pour l'affichage
     */
    private String url;

    /**
     * Le nom du fichier téléchargé par le client.
     */
    private String nom;

    /**
     * Retourne l'id de l'objet Produit.
     *
     * @return l'id de l'objet Produit
     */
    public int getId() {
        return id;
    }

    /**
     * Remplace l'id de l'objet Produit par celui-ci mit en paramètre.
     *
     * @param id Le nouveau ID de la photo
     */
    public void setIdPhoto(int id) {
        this.id = id;
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

    /**
     * Récupère le nom originel de la photo (le nom du fichier téléchargé)
     *
     * @return le nom du fichier originel
     */
    public String getNom() {
        return nom;
    }

    /**
     * Fixe le nom originel du fichier
     *
     * @param n le nom du fichier
     */
    public void setNom(String n) {
        nom = n;
    }
}
