package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;

import java.util.List;

/**
 * Interface de CaracteristiqueTypeBusiness
 */
public interface ICaracteristiqueTypeBusiness {
    /**
     * Méthode définissant l'ajout de d'un type de caractéristique.
     * @param type Le type de la caractéristique
     * @return l'objet TypeCaracteristique crée
     */
    TypeCaracteristiqueDTO addTypeCaracteristique(String type);

    /**
     * Méthode définissant la modification d'un type de caractéristique.
     * @param idTypeCaracteristique Id du type de caracteristique à modifier.
     * @param type Le nouveau type de caractéristique
     * @return l'objet TypeCaracteristique modifié
     */
    TypeCaracteristiqueDTO updateTypeCaracteristique(int idTypeCaracteristique, String type);

    /**
     * Méthode définissant la supprésion d'un type de caractéristique.
     * @param idTypeCaracteristique Id du type de caractéristique à supprimer
     * @return true
     */
    boolean deleteTypeCaracteristique(int idTypeCaracteristique);

    /**
     * Méthode définissant la recherche de tous les types de caractéristique.
     * @return une liste de type de caractéristique
     */
    List<TypeCaracteristiqueDTO> getTypeCaracteristique();

    /**
     * Méthode définissant la recherche d'un type de caractéristique selon l'id du type caractéristique.
     * @param idTypeCaracteristique L'id du type de caractéristique recherché.
     * @return l'objet TypeCaracteristique recherché
     */
    TypeCaracteristiqueDTO getTypeCaracteristiqueByID(int idTypeCaracteristique);
}
