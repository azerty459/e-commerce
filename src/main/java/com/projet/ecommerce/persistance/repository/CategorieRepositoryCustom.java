package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.Collection;
import java.util.HashMap;

public interface CategorieRepositoryCustom {

    Collection<Categorie> findAllWithCriteria(int id, String nom);

    Collection<Categorie> findParents(HashMap<Integer,Categorie> cats);

}
