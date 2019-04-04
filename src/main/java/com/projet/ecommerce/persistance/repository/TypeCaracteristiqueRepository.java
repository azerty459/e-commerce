package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCaracteristiqueRepository extends CrudRepository<TypeCaracteristique, Integer> {
    
}
