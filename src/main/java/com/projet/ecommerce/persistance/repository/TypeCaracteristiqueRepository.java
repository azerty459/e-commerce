package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TypeCaracteristiqueRepository extends CrudRepository<TypeCaracteristique, Integer> {

    @Override
    Collection<TypeCaracteristique> findAll();

}
