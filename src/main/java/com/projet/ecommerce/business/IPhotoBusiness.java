package com.projet.ecommerce.business;

import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.List;

/**
 * Interface de PhotoBusiness
 */
public interface IPhotoBusiness {
    /**
     * Méthode définissant l'ajout d'une photo.
     * @param url URL pointant sur une image.
     * @param produit Un objet produit
     * @return l'objet photo créé
     */
    Photo addPhoto(String url, Produit produit);

    /**
     *  Méthode définissant la modification d'une photo.
     * @param idPhoto Id de la photo à modifier
     * @param url La nouvelle URL pointant sur une image
     * @param produit
     * @return l'objet photo modifié
     */
    Photo updatePhoto(int idPhoto, String url, Produit produit);

    /**
     * Méthode définissant la suppression d'une photo.
     * @param idPhoto L'id de la photo à supprimer.
     * @return true
     */
    boolean deletePhoto(int idPhoto);

    /**
     * Méthode définissant la recherche de toutes les photos.
     * @return une liste de photo
     */
    List<Photo> getPhoto();

    /**
     * Méthode définissant la recherche d'une photo selon l'id de la photo recherché.
     * @param idPhoto Id de la photo à rechercher
     * @return l'objet photo recherché
     */
    Photo getPhotoByID(int idPhoto);
}
