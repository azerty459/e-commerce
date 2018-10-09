package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;

public interface ICaracteristiqueBusiness {
    /**
     * Méthode définissant l'ajout d'une caractéristique
     * @param caracteristique
     * @return
     */
    CaracteristiqueDTO add(CaracteristiqueDTO caracteristique);

}
