package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

/**
 * Interface du service CaracteristiqueBusiness
 */

public interface ICaracteristiqueBusiness {

    /**
     * Méthode permettant d'ajouter une caractéristique à un produit
     * @param referenceProduit
     * @param typeCaracteristique
     * @param valeurCaracteristique
     * @return
     */
    public CaracteristiqueDTO addCaracteristique(String referenceProduit, String typeCaracteristique, String valeurCaracteristique);

    /**
     * Méthode permettant de modifier une caractéristique d'un produit
     * @param caracteristique
     * @return
     */
    public CaracteristiqueDTO updateCaracteristique(Caracteristique caracteristique);
    
    /**
     * Méthode permettant de supprimer une caractéristique d'un produit
     * @param referenceProduit
     * @param typeCaracteristique
     * @return
     */
    public boolean deleteCaracteristique(String referenceProduit, String typeCaracteristique);

}

