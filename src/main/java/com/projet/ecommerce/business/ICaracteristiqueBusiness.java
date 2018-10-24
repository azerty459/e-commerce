package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;

import java.util.List;

public interface ICaracteristiqueBusiness {
    /**
     * Méthode définissant l'ajout d'une caractéristique
     * @param caracteristique à ajouter
     * @return
     */
    CaracteristiqueDTO add(CaracteristiqueDTO caracteristique);

    /**
     * Méthode définissant la modification d'une caractéristique
     * @param caracteristique à modifier
     * @return
     */
    CaracteristiqueDTO update(CaracteristiqueDTO caracteristique);

    /**
     * Méthode définissant la suppression d'une caractéristique
     * @param id de la caractéristique à supprimer
     * @return
     */
    boolean delete(int id);

    /**
     * Méthode retournant la liste des caractéristiques
     * @return la liste de toutes les caractéristiques
     */

    List<CaracteristiqueDTO> getAll();


}
