package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CategorieTransformer {

    private CategorieTransformer() {
    }

    /**
     * Transforme une collection d'objets CategorieDTO en une collection d'objets Categorie.
     *
     * @param categorieDTOCollection Une collection d'objets CategorieDTO
     * @return une collection d'objets Categorie
     */
    public static Collection<Categorie> dtoToEntity(Collection<CategorieDTO> categorieDTOCollection) {
        List<Categorie> categorieList = new ArrayList<>();
        for (CategorieDTO categorieDTO : categorieDTOCollection) {
            categorieList.add(dtoToEntity(categorieDTO));
        }
        return categorieList;
    }

    /**
     * Transforme un objet CategorieDTO en Categorie
     *
     * @param categorieDTO Un objet CategorieDTO
     * @return un objet Categorie
     */
    public static Categorie dtoToEntity(CategorieDTO categorieDTO) {
        Categorie categorie = new Categorie();
        categorie.setNomCategorie(categorieDTO.getNom());
        categorie.setIdCategorie(categorieDTO.getId());
        categorie.setLevel(categorieDTO.getLevel());
        return categorie;
    }

    /**
     * Transforme un objet Categorie en CategorieDTO
     *
     * @param categorie Un objet Categorie
     * @return un objet CategorieDTO
     */
    public static CategorieDTO entityToDto(Categorie categorie) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setNom(categorie.getNomCategorie());
        categorieDTO.setSousCategories(new ArrayList<>());
        categorieDTO.setId(categorie.getIdCategorie());
        categorieDTO.setLevel(categorie.getLevel());
        return categorieDTO;
    }

    /**
     * Transforme un objet Categorie en CategorieDTO
     *
     * @param categorie           Un objet Categorie
     * @param categorieCollection Une collection contenant des objets Categorie
     * @param chemins             HashMap associant chaque catégorie à un chemin (sous la forme d'une chaîne de caractères)
     * @param parentDirect        la catégorie directement parente de categorie
     * @return un objet CategorieDTO
     */
    public static CategorieDTO entityToDto(Categorie categorie, Collection<Categorie> categorieCollection, HashMap<Categorie, Collection<Categorie>> chemins, Boolean parent, Categorie parentDirect) {
        CategorieDTO categorieDTO = entityToDto(categorie);

        categorieDTO.getSousCategories().clear();
        categorieDTO.getSousCategories().addAll(getSousCategorie(categorie, categorieCollection));

        int levelMax = Integer.MIN_VALUE;
        for (Categorie retourCategorie : categorieCollection) {
            if (retourCategorie.getLevel() > levelMax) {
                levelMax = retourCategorie.getLevel();
            }
        }
        categorieDTO.setProfondeur(levelMax);

        // Renseignement du level de la catégorie
        categorieDTO.setLevel(categorie.getLevel());

        // Ajout de son chemin
        Collection<Categorie> ch = chemins.get(categorie);
        Collection<CategorieDTO> cheminDto = new ArrayList<>();
        if (ch == null) {

        } else {
            for (Categorie cat : ch) {
                cheminDto.add(entityToDto(cat));
            }
            categorieDTO.setChemin(cheminDto);
        }


        // Ajout de son parent direct s'il est donné
        if (parent && parentDirect != null) {
            categorieDTO.setParent(entityToDto(parentDirect));
        } else {
            categorieDTO.setParent(null);
        }

        return categorieDTO;
    }

    // TODO: seule fonction entityToDto qui rajoute les chemins. Faire en sorte que les autres entityToDto le fasse aussi.

    /**
     * Transforme une liste d'objets Categorie en une collection d'objets CategorieDTO.
     *
     * @param categorieCollection Une collection d'objets Categorie
     * @param chemins             HashMap associant chaque catégorie à un chemin (sous la forme d'une chaîne de caractères)
     * @param sousCat             true si on veut les sous-catégories, false sinon
     * @param parentDirect        la catégorie directement parente de la catégorie unique de categorieList
     * @return une collection d'objets CategorieDTO
     */
    public static Collection<CategorieDTO> entityToDto(Collection<Categorie> categorieCollection, HashMap<Categorie, Collection<Categorie>> chemins, Boolean sousCat, Boolean parent, Categorie parentDirect) {
        List<CategorieDTO> categorieDTOList = new ArrayList<>();
        if (!categorieCollection.isEmpty()) {
            int levelMin = Integer.MAX_VALUE;
            for (Categorie categorie : categorieCollection) {
                if (categorie.getLevel() < levelMin) {
                    levelMin = categorie.getLevel();
                }
            }

            for (Categorie categorie : categorieCollection) {
                if (!sousCat || categorie.getLevel() == levelMin) {
                    categorieDTOList.add(entityToDto(categorie, categorieCollection, chemins, parent, parentDirect));
                }
            }
        }
        return categorieDTOList;
    }

    /**
     * Algorithme pour trouver les sous-catégories d'une catégorie.
     *
     * @param categorie           La catégorie où on veut trouver les sous-catégories.
     * @param categorieCollection Une collection d'objets Categorie
     * @return une liste d'objets CategorieDTO représentant les sous-catégories
     */
    private static Collection<CategorieDTO> getSousCategorie(Categorie categorie, Collection<Categorie> categorieCollection) {
        List<CategorieDTO> categorieDTOList = new ArrayList<>();
        for (Categorie categorieFor : categorieCollection) {
            boolean sousCategorieDirecte = categorie.getLevel() + 1 == categorieFor.getLevel();
            boolean estMoinsAGauche = categorie.getBorneGauche() < categorieFor.getBorneGauche();
            boolean estMoinsADroite = categorie.getBorneDroit() > categorieFor.getBorneDroit();
            if (sousCategorieDirecte && estMoinsAGauche && estMoinsADroite) {
                CategorieDTO categorieDTO = entityToDto(categorieFor);
                categorieDTO.getSousCategories().addAll(getSousCategorie(categorieFor, categorieCollection));
                categorieDTOList.add(categorieDTO);
            }
        }
        return categorieDTOList;
    }
}
