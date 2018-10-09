package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, Integer> {

    // pour findall, extends l'interface pagingdandsorting

}
