package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.projet.ecommerce.utilitaire.Utilitaire.mergeObjects;

@Service
public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness {

    @Autowired
    TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    @Override
    public Collection<TypeCaracteristiqueDTO> getAll() {
        Collection<TypeCaracteristique> typeCaracteristiques = new ArrayList<>();
        typeCaracteristiques.addAll(typeCaracteristiqueRepository.findAll());
        return TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiques);
    }

    @Override
    public TypeCaracteristiqueDTO add(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
        if (typeCaracteristiqueDTO == null || typeCaracteristiqueDTO.getLibelle().isEmpty()){
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout du type (le libelllé ne peut être null");
            graphQLCustomException.ajouterExtension("libelle", typeCaracteristiqueDTO.getLibelle());
            throw graphQLCustomException;
        }

        TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO);
        return TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepository.save(typeCaracteristique));

    }

    @Override
    public TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
        if (typeCaracteristiqueDTO == null) {
            return null;
        }

        Optional<TypeCaracteristique> optionalTypeCaracteristique = typeCaracteristiqueRepository
                .findById(typeCaracteristiqueDTO.getIdTypeCaracteristique());

        optionalTypeCaracteristique.orElseThrow(() -> new GraphQLCustomException("Le type de caractéristique recherché n'existe pas."));

        TypeCaracteristique retourTypeCaracteristique = optionalTypeCaracteristique.get();

        TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO);
        // On fusionne les deux types en un
        TypeCaracteristique typeCaracteristiqueFinal;
        try {
            typeCaracteristiqueFinal = mergeObjects(typeCaracteristique, retourTypeCaracteristique);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new GraphQLCustomException("Le type de caractéristique ne peut pas être sauvegardé");
        }

        // On retourne le type final et on le transforme en DTO
        return TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepository.save(typeCaracteristiqueFinal));
    }

    @Override
    public boolean delete(Integer idTypeCaracteristique) {
        if (idTypeCaracteristique == null) {
            return false;
        }
        Optional<TypeCaracteristique> optionalTypeCaracteristique = typeCaracteristiqueRepository.findById(idTypeCaracteristique);
        optionalTypeCaracteristique.orElseThrow(() -> new GraphQLCustomException("Le type de caractéristique recherché n'existe pas."));
        typeCaracteristiqueRepository.deleteById(idTypeCaracteristique);
        return true;
    }
}
