package com.projet.ecommerce.business;

import java.util.Collection;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;

/**
 * On ajoute ce qui concerne les caractéristiques : ajouter une caractéristique
 * au produit obtenir toutes les caractéristiques (en filtrant par idLibelle, ou
 * par mot-clé devant être contenu ou bien dans le nom du libellé ou bien dans
 * la valeur) modifier une caractéristique supprimer une caractéristique
 */

public interface ICaracteristiqueBusiness {
    CaracteristiqueDTO addCaracteristique(String refProduit, int idLibelle, String valeurCaracteristique);
    Collection<CaracteristiqueDTO> getAllCaracteristiques(String refProduit);
    Collection<CaracteristiqueDTO> getAllCaracteristique(String refProduit, String motCle);
    CaracteristiqueDTO getCaracteristique(String refProduit, int idLibelle);
    CaracteristiqueDTO updateCaracteristique(String refProduit, int idLibelle, String nouvelleValeur);
    void deleteCaracteristique(String refProduit, int idLibelle);
}