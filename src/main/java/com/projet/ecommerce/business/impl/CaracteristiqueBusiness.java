package com.projet.ecommerce.business.impl;

import static com.projet.ecommerce.utilitaire.Utilitaire.mergeObjects;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {
    
    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Override
    public CaracteristiqueDTO add(String libelle) {
        if(libelle == null || libelle.trim().isEmpty()) {
            GraphQLCustomException gqlex = new GraphQLCustomException("Erreur dans l'ajout de la caracteristique (le libellé ne peut pas être null)");
            gqlex.ajouterExtension("libelle", libelle);
            throw gqlex;
        }
        Caracteristique c = new Caracteristique();
        c.setLibelle(libelle);
        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(c));
    }

    @Override
    public CaracteristiqueDTO update(CaracteristiqueDTO carac) {
        if(carac == null) {
            return null;
        } else if(carac.getLibelle().trim().isEmpty()) {
            GraphQLCustomException gqlex = new GraphQLCustomException("Erreur dans l'ajout de la caracteristique (le libellé ne peut pas être null)");
            gqlex.ajouterExtension("libelle", carac.getLibelle());
            throw gqlex;
        }
        //Recherche dans la bse si il existe deja
        Optional<Caracteristique> optCarac = caracteristiqueRepository.findById(carac.getId());
        optCarac.orElseThrow(() -> new GraphQLCustomException("La caractéristique recherchée n'existe pas"));
        Caracteristique caracteristiqueBase = optCarac.get();
        Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(carac);
        
        try {
            Caracteristique caracteristique1Result = mergeObjects(caracteristiqueBase, caracteristique);
            return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique1Result));
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new GraphQLCustomException("Le type de caractéristique ne peut pas être sauvegardé");
        }
    }

    @Override
    public boolean delete(int id) {
        Optional<Caracteristique> optCarac = caracteristiqueRepository.findById(id);
        if(!optCarac.isPresent()) {
            return false;
        }
        caracteristiqueRepository.deleteById(id);
        return true;

    }

    @Override
    public Collection<CaracteristiqueDTO> getAll() {
        Iterable<Caracteristique> collectionCarac = caracteristiqueRepository.findAll();
        return CaracteristiqueTransformer.entityToDto(collectionCarac);
    }
    
}
