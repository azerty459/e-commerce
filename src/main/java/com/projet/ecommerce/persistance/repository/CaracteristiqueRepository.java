package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, Integer> {

    Collection<Caracteristique> getAll();

    Caracteristique getById_caracteristique(int id);

    <S extends Caracteristique> S save(@NotNull S caracteristique);
}
