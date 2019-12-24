package com.projet.ecommerce.business.dto.transformer;


import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TypeCaracteristiqueTransformer {

    /**
     * Transforme une collection d'objets {@link TypeCaracteristiqueDTO} en une collection d'objets {@link TypeCaracteristique}.
     *
     * @param listTypeCaracteristiqueDTO Une collection d'objets TypeCaracteristiqueDTO
     * @return une collection d'objets AvisClient
     */

    @Autowired
    private ModelMapper modelMapper;

    public Collection<TypeCaracteristique> listDtoToEntity(Collection<TypeCaracteristiqueDTO> listTypeCaracteristiqueDTO) {
        if (listTypeCaracteristiqueDTO == null) return null;

        return listTypeCaracteristiqueDTO.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());

    }

    private TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {

        TypeCaracteristique typeCaracteristique = modelMapper.map(typeCaracteristiqueDTO, TypeCaracteristique.class);
        typeCaracteristique.setId_type_caracteristique(typeCaracteristiqueDTO.getId_type_caracteristique());
        typeCaracteristique.setLibelle(typeCaracteristiqueDTO.getLibelle());

        return typeCaracteristique;


    }
}
