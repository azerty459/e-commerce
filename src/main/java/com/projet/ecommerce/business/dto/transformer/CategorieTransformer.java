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
    public static Collection<Categorie> dtoToEntity(List<CategorieDTO> categorieDTOCollection){
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
    private static Categorie dtoToEntity(CategorieDTO categorieDTO){
        Categorie categorie = new Categorie();
        categorie.setNomCategorie(categorieDTO.getNom());
        categorie.setIdCategorie(categorieDTO.getId());
        return categorie;
    }

    /**
     * Transforme une liste d'objets Categorie en une collection d'objets CategorieDTO.
     * @param categoriesList Une liste de d'objets Categorie
     * @return une collection d'objets CategorieDTO
     */
    public static Collection<CategorieDTO> entityToDto(List<Categorie> categoriesList, Boolean sousCat) {
        List<CategorieDTO> categorieDTOList = new ArrayList<>();
        int levelMin = categoriesList.get(0).getLevel();
        for(int i=0; i<categoriesList.size(); i++) {
            if(categoriesList.get(i).getLevel() < levelMin){
                levelMin = categoriesList.get(i).getLevel();
            }
        }
        for(int i=0; i<categoriesList.size(); i++) {
            if(sousCat && categoriesList.get(i).getLevel() == levelMin)
                categorieDTOList.add(entityToDto(categoriesList.get(i), categoriesList));
            else if (!sousCat)
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
        categorieDTO.setId(categorie.getIdCategorie());
        // System.out.println(categoriesList.size());
        return categorieDTO;
    }

    /**
     * Transforme un objet Categorie en CategorieDTO
     * @param categorie Un objet Categorie
     * @return un objet CategorieDTO
     */
    public static CategorieDTO entityToDto(Categorie categorie) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setNom(categorie.getNomCategorie());
        categorieDTO.setSousCategories(new ArrayList<>());
        return categorieDTO;
    }


    /**
     * Algorithme pour trouver les sous-catégories d'une catégorie.
     * @param categorie La catégorie où on veut trouver les sous-catégories.
     * @param categorieList Une liste de d'objets Categorie
     * @return une liste d'objets CategorieDTO représentant les sous-catégories
     */
    private static Collection<CategorieDTO> getSousCategorie (Categorie categorie , List<Categorie> categorieList) {
        List<CategorieDTO> categorieDTOList = new ArrayList<>();
        for(int i=0; i<categorieList.size(); i++) {
            if(categorie.getLevel()+1 == categorieList.get(i).getLevel() && categorie.getBorneGauche() < categorieList.get(i).getBorneGauche() &&  categorie.getBorneDroit() > categorieList.get(i).getBorneDroit()) {
                CategorieDTO categorieDTO = new CategorieDTO();
                categorieDTO.setNom(categorieList.get(i).getNomCategorie());
                categorieDTO.setSousCategories(new ArrayList<>(getSousCategorie(categorieList.get(i), categorieList)));
                categorieDTO.setId(categorieList.get(i).getIdCategorie());
                categorieDTOList.add(categorieDTO);
            }
        }
        return categorieDTOList;
    }
}
