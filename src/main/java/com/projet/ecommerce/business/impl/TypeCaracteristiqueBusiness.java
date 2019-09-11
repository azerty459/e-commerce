package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness {

    @Autowired
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    @Override
    public List<TypeCaracteristiqueDTO> getAll() {
        return new ArrayList<>(TypeCaracteristiqueTransformer.entityToDto(new ArrayList<>(typeCaracteristiqueRepository.findAll())));
    }
}
