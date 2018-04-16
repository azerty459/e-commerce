package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICaracteristiqueTypeBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service permettant de gérer les actions effectuées pour les types de caractéristiques.
 */

@Service
public class CaracteristiqueTypeBusiness implements ICaracteristiqueTypeBusiness {

    @Autowired
    private TypeCaracteristiqueRepository TypecaracteristiqueRepository;

    /**
     * Ajoute un type de caracteristique dans la base de données.
     *
     * @param type Le type de la caractéristique
     * @return l'objet TypeCaracteristique crée
     */
    @Override
    public TypeCaracteristiqueDTO addTypeCaracteristique(String type) {
        TypeCaracteristique caracteristiqueType = new TypeCaracteristique();
        caracteristiqueType.setType(type);
        return TypeCaracteristiqueTransformer.entityToDto(TypecaracteristiqueRepository.save(caracteristiqueType));
    }

    /**
     * Modifie le type de caracteristique dans la base de données.
     *
     * @param idTypeCaracteristique Id du type de caracteristique à modifier.
     * @param type                  Le nouveau type de caractéristique
     * @return l'objet TypeCaracteristique modifié, null s'il n'est pas trouvée
     */
    @Override
    public TypeCaracteristiqueDTO updateTypeCaracteristique(int idTypeCaracteristique, String type) {
        Optional<TypeCaracteristique> tempCaracteristiqueTypeRepository = TypecaracteristiqueRepository.findById(idTypeCaracteristique);
        if (tempCaracteristiqueTypeRepository.isPresent() == true) {
            TypeCaracteristique caracteristiqueType = tempCaracteristiqueTypeRepository.get();
            caracteristiqueType.setType(type);
            return TypeCaracteristiqueTransformer.entityToDto(TypecaracteristiqueRepository.save(caracteristiqueType));
        }
        return null;
    }

    /**
     * Supprimer le type de caracteristique dans la base de données.
     *
     * @param idTypeCaracteristique Id du type de caractéristique à supprimer
     * @return true
     */
    @Override
    public boolean deleteTypeCaracteristique(int idTypeCaracteristique) {
        TypecaracteristiqueRepository.deleteById(idTypeCaracteristique);
        return true;
    }

    /**
     * Retourne la liste complète des types de caractéristique liés à la base de données.
     *
     * @return une liste de type de caractéristique.
     */
    @Override
    public List<TypeCaracteristiqueDTO> getTypeCaracteristique() {
        return new ArrayList<>(TypeCaracteristiqueTransformer.entityToDto(TypecaracteristiqueRepository.findAll()));
    }

    /**
     * Retourne le type de caractéristique recherché.
     *
     * @param idTypeCaracteristique L'id du type de caractéristique recherché.
     * @return l'objet TypeCaracteristique recherché sinon null, s'il n'est pas trouvé
     */
    @Override
    public TypeCaracteristiqueDTO getTypeCaracteristiqueByID(int idTypeCaracteristique) {
        Optional<TypeCaracteristique> caracteristiqueOptional = TypecaracteristiqueRepository.findById(idTypeCaracteristique);
        return TypeCaracteristiqueTransformer.entityToDto(caracteristiqueOptional.orElse(null));
    }
}
