package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCaracteristiqueRepository extends CrudRepository<TypeCaracteristique, Integer> {

    @NotNull
    Iterable<TypeCaracteristique> findAll();

    TypeCaracteristique getById_type_caracteristique(int id) ;

    <S extends TypeCaracteristique> S save(@NotNull S typeCaracteristique) ;



}
