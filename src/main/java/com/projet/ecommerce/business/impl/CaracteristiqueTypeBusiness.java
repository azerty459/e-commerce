package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICaracteristiqueTypeBusiness;
import com.projet.ecommerce.entity.TypeCaracteristique;
import com.projet.ecommerce.repository.TypeCaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaracteristiqueTypeBusiness implements ICaracteristiqueTypeBusiness {

    @Autowired
    private TypeCaracteristiqueRepository TypecaracteristiqueRepository;

    /**
     * Ajoute un type de caracteristique dans la base de données.
     * @param type Le type de la caractéristique
     * @return l'objet TypeCaracteristique crée
     */
    @Override
    public TypeCaracteristique addTypeCaracteristique(String type) {
        TypeCaracteristique caracteristiqueType = new TypeCaracteristique();
        caracteristiqueType.setType(type);
        TypecaracteristiqueRepository.save(caracteristiqueType);
        return caracteristiqueType;
    }

    /**
     * Modifie le type de caracteristique dans la base de données.
     * @param idTypeCaracteristique Id du type de caracteristique à modifier.
     * @param type Le nouveau type de caractéristique
     * @return l'objet TypeCaracteristique modifié, null s'il n'est pas trouvée
     */
    @Override
    public TypeCaracteristique updateTypeCaracteristique(int idTypeCaracteristique, String type) {
        Optional<TypeCaracteristique> tempCaracteristiqueTypeRepository = TypecaracteristiqueRepository.findById(idTypeCaracteristique);
        if (tempCaracteristiqueTypeRepository.isPresent() == true){
            TypeCaracteristique caracteristiqueType = tempCaracteristiqueTypeRepository.get();
            caracteristiqueType.setType(type);
            TypecaracteristiqueRepository.save(caracteristiqueType);
            return caracteristiqueType;
        }
        return null;
    }

    /**
     * Supprimer le type de caracteristique dans la base de données.
     * @param idTypeCaracteristique Id du type de caractéristique à supprimer
     * @return true
     */
    @Override
    public boolean deleteTypeCaracteristique(int idTypeCaracteristique) {
        TypecaracteristiqueRepository.delete(TypecaracteristiqueRepository.findById(idTypeCaracteristique).get());
        return true;
    }

    /**
     * Retourne la liste complète des types de caractéristique liés à la base de données.
     * @return une liste de type de caractéristique.
     */
    @Override
    public List<TypeCaracteristique> getTypeCaracteristique() {
        return TypecaracteristiqueRepository.findAll();
    }

    /**
     * Retourne le type de caractéristique recherché.
     * @param idTypeCaracteristique L'id du type de caractéristique recherché.
     * @return l'objet TypeCaracteristique recherché sinon null, s'il n'est pas trouvé
     */
    @Override
    public TypeCaracteristique getTypeCaracteristiqueByID(int idTypeCaracteristique) {
        Optional<TypeCaracteristique> CaracteristiqueTypeRepository = TypecaracteristiqueRepository.findById(idTypeCaracteristique);
        if (CaracteristiqueTypeRepository.isPresent() == true){
            return CaracteristiqueTypeRepository.get();
        }
        return null;
    }
}
