package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface TypeCaracteristiqueRepository extends CrudRepository<TypeCaracteristique, String> {

    @Override
    Collection<TypeCaracteristique> findAll();
}
