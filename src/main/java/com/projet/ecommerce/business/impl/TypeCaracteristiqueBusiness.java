package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import graphql.GraphQLException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness {

    @Autowired
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    @Override
    public List<TypeCaracteristiqueDTO> get() {
        return TypeCaracteristiqueTransformer.entitiesToDto(typeCaracteristiqueRepository.findAll());
    }

    @Override
    public TypeCaracteristiqueDTO create(TypeCaracteristiqueDTO dto) {
        TypeCaracteristique entity = TypeCaracteristiqueTransformer.dtoToEntity(dto);
        entity = typeCaracteristiqueRepository.saveAndFlush(entity);
        return TypeCaracteristiqueTransformer.entityToDto(entity);
    }

    @Override
    public TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO dto) {
        TypeCaracteristique entity = typeCaracteristiqueRepository.findById(dto.getId()).orElseThrow(GraphQLException::new);
        entity.setNom(dto.getNom());
        typeCaracteristiqueRepository.save(entity);
        return dto;
    }
}
