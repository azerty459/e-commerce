package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);
    
    /**
     * Recupère la liste des produits correspondant aux critères
     * 
     * @param noteSup La note moyenne doit être supérieur à cette valeur (-1 pour ignorer)
     * @param noteInf La note moyenne doit être inférieur à cette valeur (-1 pour ignorer)
     * @param nomProduit Le nom exact du produit
     * @param nomProduitContient Le nom du produit doit contenir cette valeur
     * @param categorie La categorie du produit
     * @return 
     */
    Collection<Produit> findByCriteria(double noteSup, double noteInf, String nomProduit, String nomProduitContient, Categorie categorie);

}
