package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.projet.ecommerce.persistance.entity.Caracteristique;

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, Integer>{

	Collection<Caracteristique> getAll();
}
