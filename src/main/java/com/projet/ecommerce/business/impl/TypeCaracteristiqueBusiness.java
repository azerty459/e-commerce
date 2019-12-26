package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service permettant de gerer les actions sur les type de caracteristiques
 */
@Service
public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness {

    @Autowired
    TypeCaracteristiqueRepository typeCaracteristiqueRepository;


    @Override
    public List<TypeCaracteristiqueDTO> getAll() {
        List<TypeCaracteristiqueDTO> typeCaracteristiquesDTO = (List<TypeCaracteristiqueDTO>) TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepository.findAll());
        if (typeCaracteristiquesDTO != null) return typeCaracteristiquesDTO;
        else return null;
    }

    @Override
    public TypeCaracteristiqueDTO getTypeCaracteristique(int id) {
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepository.findById(id).get());
        if (typeCaracteristiqueDTO != null) return typeCaracteristiqueDTO;
        else return null;
    }

    @Override
    public TypeCaracteristiqueDTO addTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
        TypeCaracteristique caracteristique = typeCaracteristiqueRepository.save(TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO));
        if (caracteristique != null) return TypeCaracteristiqueTransformer.entityToDto(caracteristique);
        else return null;
    }


}
