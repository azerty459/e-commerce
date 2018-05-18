package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.core.io.Resource;
import java.util.List;

/**
 * Interface du service PhotoBusiness.
 */

public interface IPhotoBusiness {

    /**
     * Méthode permettant l'upload d'une image
     *
     * @param fichier le fichier a upload
     * @param refProduit ref du produit
     * @return true si le fichier est upload , false sinon
     */
    Boolean upload(MultipartFile fichier, String refProduit);

    Resource loadPhotos(String nomFichier,String refProduit);
//    /**
//     * Méthode définissant la recherche de toutes les photos.
//     *
//     * @return une liste de photo
//     */
//    List<Photo> getPhoto();

    /**
     * Méthode définissant la recherche des photos selon les paramètres ci-dessous
     * @param ref la référence du produit recherché
     * @return la liste des photos trouvés
     */
    List<PhotoDTO> getAll(String ref);


//    /**
//     * Méthode définissant l'ajout d'une photo.
//     *
//     * @param url     URL pointant sur une image.
//     * @param produit Un objet produit
//     * @return l'objet photo créé
//     */
//    PhotoDTO addPhoto(String url, Produit produit);
//
//    /**
//     * Méthode définissant la modification d'une photo.
//     *
//     * @param idPhoto Id de la photo à modifier
//     * @param url     La nouvelle URL pointant sur une image
//     * @param produit
//     * @return l'objet photo modifié
//     */
//    Photo updatePhoto(int idPhoto, String url, Produit produit);
//
//    /**
//     * Méthode définissant la suppression d'une photo.
//     *
//     * @param idPhoto L'id de la photo à supprimer.
//     * @return true
//     */
//    boolean deletePhoto(int idPhoto);


//
//    /**
//     * Méthode définissant la recherche d'une photo selon l'id de la photo recherché.
//     *
//     * @param idPhoto Id de la photo à rechercher
//     * @return l'objet photo recherché
//     */
//    Photo getPhotoByID(int idPhoto);


}
