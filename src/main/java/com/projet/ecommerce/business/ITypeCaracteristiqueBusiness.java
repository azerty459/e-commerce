package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;

import java.util.Collection;

public interface ITypeCaracteristiqueBusiness {

    Collection<TypeCaracteristiqueDTO> getAll();

    TypeCaracteristiqueDTO add(TypeCaracteristiqueDTO typeCaracteristiqueDTO);

    TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO typeCaracteristiqueDTO);

    boolean delete(Integer idTypeCaracteristique);

}
