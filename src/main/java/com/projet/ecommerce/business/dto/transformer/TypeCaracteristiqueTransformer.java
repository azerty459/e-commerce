package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TypeCaracteristiqueTransformer {

    private TypeCaracteristiqueTransformer() {

    }

    public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO dto) {
        TypeCaracteristique entity = new TypeCaracteristique();
        entity.setId(dto.getId());
        entity.setNom(dto.getNom());
        return entity;
    }

    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique entity) {
        TypeCaracteristiqueDTO dto = new TypeCaracteristiqueDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        return dto;
    }

    public static List<TypeCaracteristique> dtoToEntities(Collection<TypeCaracteristiqueDTO> dto) {
        return dto.stream().map(TypeCaracteristiqueTransformer::dtoToEntity).collect(Collectors.toList());
    }

    public static List<TypeCaracteristiqueDTO> entitiesToDto(Collection<TypeCaracteristique> entities) {
        return entities.stream().map(TypeCaracteristiqueTransformer::entityToDto).collect(Collectors.toList());
    }

}
