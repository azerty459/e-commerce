package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service permettant de gérer les actions effectuées pour les caractéristiques.
 */

@Service
public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    /**
     * Ajoute une caracteristique dans la base de données.
     *
     * @param typeCaracteristique Objet type caracteristique
     * @param valeur              Valeur
     * @return la caracteristique crée.
     */
    @Override
    public CaracteristiqueDTO addCaracteristique(TypeCaracteristique typeCaracteristique, String valeur) {
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setTypeCaracteristique(typeCaracteristique);
        caracteristique.setValeur(valeur);
        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }

    /**
     * Modifie une caractéristique dans la base de données.
     *
     * @param idCaracteristique   Id de la caractéristiqe à modifier
     * @param typeCaracteristique le nouveau objet TypeCaracteristique liée à la caractéristique
     * @param valeur              la nouvelle valeur associée à la caractéristique
     * @return la caractéristique modifié, null si elle n'est pas trouvée
     */
    @Override
    public CaracteristiqueDTO updateCaracteristique(int idCaracteristique, TypeCaracteristique typeCaracteristique, String valeur) {
        Optional<Caracteristique> tempCaracteristique = caracteristiqueRepository.findById(idCaracteristique);
        if (tempCaracteristique.isPresent() == true) {
            Caracteristique caracteristique = tempCaracteristique.get();
            caracteristique.setTypeCaracteristique(typeCaracteristique);
            caracteristique.setValeur(valeur);
            return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
        }
        return null;
    }

    /**
     * Supprime une caractéristique dans la base de données.
     *
     * @param idCaracteristique Id de la caractéristique à supprimer
     * @return true
     */
    @Override
    public boolean deleteCaracteristique(int idCaracteristique) {
        caracteristiqueRepository.deleteById(idCaracteristique);
        return true;
    }

    /**
     * Retourne la liste complète des caractéristiques liés à la base de données.
     *
     * @return une liste de caracteristique
     */
    @Override
    public List<CaracteristiqueDTO> getCaracteristique() {
        return new ArrayList<>(CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.findAll()));
    }

    /**
     * Retourne la caractéristique recherché.
     *
     * @param idCaracteristique Id de la caractéristique à rechercher
     * @return l'objet caractéristique recherché sinon null, s'il n'est pas trouvé
     */
    @Override
    public CaracteristiqueDTO getCaracteristiqueByID(int idCaracteristique) {
        Optional<Caracteristique> caracteristique = caracteristiqueRepository.findById(idCaracteristique);
        return CaracteristiqueTransformer.entityToDto(caracteristique.orElse(null));
    }
}
