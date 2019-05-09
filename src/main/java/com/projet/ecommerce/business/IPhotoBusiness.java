package com.projet.ecommerce.business;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.impl.PhotoException;
import com.projet.ecommerce.entrypoint.image.DimensionImage;

/**
 * Interface du service PhotoBusiness.
 */

public interface IPhotoBusiness {

    /**
     * Méthode permettant l'upload d'une image
     *
     * @param fichier    le fichier a upload
     * @param refProduit ref du produit
     * @return true si le fichier est upload , false sinon
     */
    Boolean upload(MultipartFile fichier, String refProduit) throws PhotoException;

    Resource loadPhotos(String nomFichier, String refProduit, DimensionImage dimension);

    /**
     * Méthode définissant la recherche des photos selon les paramètres ci-dessous
     *
     * @param ref la référence du produit recherché
     * @return la liste des photos trouvés
     */
    List<PhotoDTO> getAll(String ref);

    Boolean remove(int idPhoto);
}
