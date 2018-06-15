package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.impl.PhotoException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    Resource loadPhotos(String nomFichier, String refProduit);

    /**
     * Méthode définissant la recherche des photos selon les paramètres ci-dessous
     *
     * @param ref la référence du produit recherché
     * @return la liste des photos trouvés
     */
    List<PhotoDTO> getAll(String ref);
}
