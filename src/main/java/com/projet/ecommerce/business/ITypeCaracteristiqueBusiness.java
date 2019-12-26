package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.List;

/**
 * Interface du service TypeCaracteristique
 */
public interface ITypeCaracteristiqueBusiness {

    public List<TypeCaracteristiqueDTO> getAll();

    public TypeCaracteristiqueDTO getTypeCaracteristique(int id);

    public TypeCaracteristiqueDTO addTypeCaracteristique (TypeCaracteristiqueDTO typeCaracteristiqueDTO);



}
