package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.Collection;

public interface CategorieRepositoryCustom {

    Collection<Categorie> findAll(String nom);

}
