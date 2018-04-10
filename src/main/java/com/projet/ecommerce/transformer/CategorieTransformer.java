package com.projet.ecommerce.transformer;

import com.projet.ecommerce.dto.CategorieDTO;
import com.projet.ecommerce.dto.ProduitDTO;
import com.projet.ecommerce.entity.Categorie;
import com.projet.ecommerce.entity.Produit;

public class CategorieTransformer {

    public Categorie categorieDTOToCategorie(CategorieDTO categorieDTO){

        return new Categorie();
    }

    public CategorieDTO categorieToCategorieDTO(Categorie categorie){
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setNomCategorie(categorie.getNomCategorie());
        return new CategorieDTO();
    }
}
