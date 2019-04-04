package com.projet.ecommerce.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

@Repository
public interface TypeCaracteristiqueRepository extends CrudRepository<TypeCaracteristique, Integer> {

}
