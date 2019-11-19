package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CaracteristiqueTransformer {

    private CaracteristiqueTransformer() {
    }

    public static Caracteristique dtoToEntity(CaracteristiqueDTO dto) {
        Caracteristique entity = new Caracteristique();
        entity.setCaracteristiquePk(new Caracteristique.CaracteristiquePk());
        entity.getCaracteristiquePk()
                .setTypeCaracteristique(TypeCaracteristiqueTransformer.dtoToEntity(dto.getTypeCaracteristique()));
        entity.setValeur(dto.getValeur());
        return entity;
    }

    public static CaracteristiqueDTO entityToDto(Caracteristique entity) {
        CaracteristiqueDTO dto = new CaracteristiqueDTO();
        dto.setTypeCaracteristique(TypeCaracteristiqueTransformer.entityToDto(
                entity.getCaracteristiquePk().getTypeCaracteristique()));
        dto.setValeur(entity.getValeur());
        return dto;
    }

    public static List<Caracteristique> dtoToEntities(Collection<CaracteristiqueDTO> dto) {
        return dto.stream().map(CaracteristiqueTransformer::dtoToEntity).collect(Collectors.toList());
    }

    public static List<CaracteristiqueDTO> entitiesToDto(Collection<Caracteristique> entities) {
        return entities.stream().map(CaracteristiqueTransformer::entityToDto).collect(Collectors.toList());
    }

}
