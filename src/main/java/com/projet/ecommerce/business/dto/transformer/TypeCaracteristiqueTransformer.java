package com.projet.ecommerce.business.dto.transformer;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class TypeCaracteristiqueTransformer {

    /**
     * Transforme une collection d'objets {@link TypeCaracteristiqueDTO} en une collection d'objets {@link TypeCaracteristique}.
     *
     * @param listTypeCaracteristiqueDTO Une collection d'objets TypeCaracteristiqueDTO
     * @return une collection d'objets AvisClient
     */


    public static Collection<TypeCaracteristique> dtoToEntity(List<TypeCaracteristiqueDTO> listTypeCaracteristiqueDTO) {

        List<TypeCaracteristique> typeCaracteristiques = new ArrayList<>();
        for (TypeCaracteristiqueDTO typeCaracteristiqueDTO : listTypeCaracteristiqueDTO) {
            typeCaracteristiques.add(dtoToEntity(typeCaracteristiqueDTO));
        }
        return typeCaracteristiques;

    }

    public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {

        TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
        typeCaracteristique.setId_type_caracteristique(typeCaracteristiqueDTO.getId_type_caracteristique());
        typeCaracteristique.setLibelle(typeCaracteristiqueDTO.getLibelle());
        return typeCaracteristique;
    }


    public static Collection<TypeCaracteristiqueDTO> entityToDto(List<TypeCaracteristique> typeCaracteristiques) {
        List<TypeCaracteristiqueDTO> typeCaracteristiqueDTOS = new ArrayList<>();
        for (TypeCaracteristique typeCaracteristique : typeCaracteristiques) {
            typeCaracteristiqueDTOS.add(entityToDto(typeCaracteristique));
        }

        return typeCaracteristiqueDTOS;
    }

    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique) {
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
        typeCaracteristiqueDTO.setId_type_caracteristique(typeCaracteristique.getId_type_caracteristique());
        typeCaracteristiqueDTO.setLibelle(typeCaracteristique.getLibelle());
        return typeCaracteristiqueDTO;
    }

}
