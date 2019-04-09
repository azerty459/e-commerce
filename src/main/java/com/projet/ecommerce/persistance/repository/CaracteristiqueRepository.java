package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, Integer> {
    
    @Override
    Collection<Caracteristique> findAll();
    
    Optional<Caracteristique> findByLibelle(String libelle);
    
}
