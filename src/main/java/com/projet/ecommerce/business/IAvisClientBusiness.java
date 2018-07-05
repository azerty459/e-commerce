package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.persistance.entity.AvisClient;

import java.util.List;

/**
 * Interface du service AvisClientBusiness.
 */

public interface IAvisClientBusiness {

    /**
     * Méthode définissant la recherche des avis clients pour un produit
     *
     * @param ref la référence du produit recherché
     * @return la liste des avis clients trouvés
     */
    List<AvisClientDTO> getAll(String ref);

    /**
     * Méthode définissant la modification d'un avis client.
     *
     * @param avisClient L'objet avis client modifié à sauvegarder
     * @return l'objet avis client modifié
     */
    AvisClientDTO update(AvisClientDTO avisClient);

    /**
     * Méthode définissant la suppression d'un avis client.
     *
     * @param idAvisClient identifiant de l'avis client à supprimer
     * @return true
     */
    boolean delete(Integer idAvisClient);

    /**
     * Méthode définissant l'ajout d'un avis client
     * @param avisClient
     * @return
     */
    AvisClientDTO add(AvisClientDTO avisClient);

}
