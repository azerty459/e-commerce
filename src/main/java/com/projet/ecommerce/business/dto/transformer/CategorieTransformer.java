package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.ArrayList;
import java.util.List;

public class CategorieTransformer {

    public static List<Categorie> dtoToEntity(List<CategorieDTO> categorieDTOCollection){
        List<Categorie> categorieCollection = new ArrayList<>();
        for (CategorieDTO categorieDTO : categorieDTOCollection) {
            categorieCollection.add(dtoToEntity(categorieDTO));
        }
        return categorieCollection;
    }

    public static Categorie dtoToEntity(CategorieDTO categorieDTO){
        Categorie categorie = new Categorie();
        categorie.setNomCategorie(categorieDTO.getNomCategorie());
        return categorie;
    }


    public static List<CategorieDTO> getSousCategorie (Categorie categorie , List<Categorie> categories) {
        List<CategorieDTO> tempCategories = new ArrayList<>();
        for(int i=0; i<categories.size(); i++) {
            if(categorie.getLevel()+1 == categories.get(i).getLevel() && categorie.getBorneGauche() < categories.get(i).getBorneGauche() &&  categorie.getBorneDroit() > categories.get(i).getBorneDroit()) {
                CategorieDTO categorieDTO = new CategorieDTO();
                categorieDTO.setNomCategorie(categories.get(i).getNomCategorie());
                categorieDTO.setSousCategories(getSousCategorie(categories.get(i), categories));
                tempCategories.add(categorieDTO);
            }
        }
        return tempCategories;
    }

    public static List<CategorieDTO> entityToDto(List<Categorie> categoriesList) {
        List<CategorieDTO> categorieDTOList = new ArrayList<>();
        for(int i=0; i<categoriesList.size(); i++) {
            categorieDTOList.add(entityToDto(categoriesList.get(i), categoriesList));
        }
        return categorieDTOList;
    }

    public static CategorieDTO entityToDto(Categorie categorie, List<Categorie> categoriesList) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setNomCategorie(categorie.getNomCategorie());
        categorieDTO.setSousCategories(getSousCategorie(categorie, categoriesList));
        return categorieDTO;
    }
}
