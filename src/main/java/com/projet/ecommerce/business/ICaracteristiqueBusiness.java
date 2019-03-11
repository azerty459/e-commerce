package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.AvisClient;

import java.util.Collection;
import java.util.List;

/**
 * Interface du service CaracteristiqueBusiness.
 */

public interface ICaracteristiqueBusiness {

    /**
     * Méthode définissant la recherche des caracteristique pour un produit
     *
     * @param ref la référence du produit recherché
     * @return la liste des caracteristiques trouvées
     */
    Collection<CaracteristiqueDTO> getAll(String ref);

    /**
     * Méthode définissant l'ajout d'un avis client
     * @param caracteristique la caractéristique à ajouter
     * @param ref la référence du produit auquel on veut ajouter une caractéristique
     * @return
     */
    CaracteristiqueDTO add(CaracteristiqueDTO caracteristique, String ref);

}
