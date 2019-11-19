package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;

import java.util.List;

public interface ITypeCaracteristiqueBusiness {

    List<TypeCaracteristiqueDTO> get();

    TypeCaracteristiqueDTO create(TypeCaracteristiqueDTO dto);

    TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO dto);
}
