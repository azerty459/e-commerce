package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategorieTransformer {

    /**
     * Transforme une collection d'objets CategorieDTO en une collection d'objets Categorie.
     * @param categorieDTOCollection Une collection d'objets CategorieDTO
     * @return une collection d'objets Categorie
     */
    public static Collection<Categorie> dtoToEntity(Collection<CategorieDTO> categorieDTOCollection){
        List<Categorie> categorieList = new ArrayList<>();
        for (CategorieDTO categorieDTO : categorieDTOCollection) {
            categorieList.add(dtoToEntity(categorieDTO));
        }
        return categorieList;
    }

    /**
     * Transforme un objet CategorieDTO en Categorie
     * @param categorieDTO Un objet CategorieDTO
     * @return un objet Categorie
     */
    public static Categorie dtoToEntity(CategorieDTO categorieDTO){
        Categorie categorie = new Categorie();
        categorie.setNomCategorie(categorieDTO.getNom());
        return categorie;
    }

    /**
     * Algorithme pour trouver les sous-catégories d'une catégorie.
     * @param categorie La catégorie où on veut trouver les sous-catégories.
     * @param categories Une liste de d'objets Categorie
     * @return une liste d'objets CategorieDTO représentant les sous-catégories
     */
    public static Collection<CategorieDTO> getSousCategorie (Categorie categorie , List<Categorie> categories) {
        List<CategorieDTO> categorieDTOList = new ArrayList<>();
        for(int i=0; i<categories.size(); i++) {
            if(categorie.getLevel()+1 == categories.get(i).getLevel() && categorie.getBorneGauche() < categories.get(i).getBorneGauche() &&  categorie.getBorneDroit() > categories.get(i).getBorneDroit()) {
                CategorieDTO categorieDTO = new CategorieDTO();
                categorieDTO.setNom(categories.get(i).getNomCategorie());
                categorieDTO.setSousCategories(new ArrayList<>(getSousCategorie(categories.get(i), categories)));
                categorieDTOList.add(categorieDTO);
            }
        }
        return categorieDTOList;
    }

    /**
     * Transforme une liste d'objets Categorie en une collection d'objets CategorieDTO.
     * @param categoriesList Une liste de d'objets Categorie
     * @return une collection d'objets CategorieDTO
     */
    public static Collection<CategorieDTO> entityToDto(List<Categorie> categoriesList) {
        List<CategorieDTO> categorieDTOList = new ArrayList<>();
        for(int i=0; i<categoriesList.size(); i++) {
            categorieDTOList.add(entityToDto(categoriesList.get(i), categoriesList));
        }
        return categorieDTOList;
    }

    /**
     * Transforme un objet Categorie en CategorieDTO
     * @param categorie Un objet Categorie
     * @param categoriesList Une liste contenant des objets Categorie
     * @return un objet CategorieDTO
     */
    public static CategorieDTO entityToDto(Categorie categorie, List<Categorie> categoriesList) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setNom(categorie.getNomCategorie());
        categorieDTO.setSousCategories(new ArrayList<>(getSousCategorie(categorie, categoriesList)));
        return categorieDTO;
    }
}
