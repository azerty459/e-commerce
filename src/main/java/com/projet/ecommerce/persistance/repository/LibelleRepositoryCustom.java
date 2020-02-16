package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import com.projet.ecommerce.persistance.entity.Libelle;

//XXX - j'ai reproduit l'approche du projet existant pour ajouter custom methodes aux repo
//car je veux faire une recherche en ignorant la casse ( - possible en ajoutant simplement methode 
//dans CrudRepo interface ?)
//est ce la bonne approche pour ajouter des methodes (non existantes chez spring) aux crudrepo ??
public interface LibelleRepositoryCustom {
    Collection<Libelle> findByNomIgnoringCase(String nom);
}
