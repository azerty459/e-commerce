package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.Collection;
import java.util.HashMap;

public interface CategorieRepositoryCustom {

    Collection<Categorie> findAllWithCriteria(int id, String nom, Boolean sousCat);

    Collection<Categorie> findParents(HashMap<Integer,Categorie> cats);

    Categorie findDirectParent(Categorie cat);

    /**
     * Trouve toutes les catégories dont la borne gauche est supérieure à celle de celle donnée en paramètre
     * @param cat la catégorie de réf"rence pour la borne gauche
     * @return une collection de catégories qui obt ube borne gauche supérieure à cat
     */
    Collection<Categorie> findAllCategoriesAvecBorneGaucheSuperieure(Categorie cat);

    int rearrangerBornes(int bg, int bd, int intervalle);

}
