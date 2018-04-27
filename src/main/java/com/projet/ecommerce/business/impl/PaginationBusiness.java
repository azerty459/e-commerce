package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IPaginationBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.PaginationDTO;
import com.projet.ecommerce.business.dto.transformer.CategorieTransformer;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service permettant de gérer les actions effectuées pour la pagination.
 */

@Service
public class PaginationBusiness implements IPaginationBusiness {

    @Autowired
    private IProduitBusiness produitBusiness;

    @Autowired
    private ICategorieBusiness categorieBusiness;

    /**
     * Retourne une liste paginée selon le type voulu, la page voulu et le nombre de produits à afficher.
     * @param type le type de la liste voulu
     * @param page la page souhaité
     * @param npp le nombre de produits à afficher dans la page paginée
     * @return une objet PaginationDTO
     */
    @Override
    public PaginationDTO getPagination(String type, int page, int npp, Boolean sousCat) {
        PaginationDTO paginationDTO = new PaginationDTO();
        if(type.equals("produit")){
            Page pageProduit = produitBusiness.getPage(page, npp);
            paginationDTO.setNbpage(pageProduit.getTotalPages());
            paginationDTO.setTotal(pageProduit.getTotalElements());
            paginationDTO.setCategories(new ArrayList<>());
            paginationDTO.setProduits(new ArrayList<>(ProduitTransformer.entityToDto(pageProduit.getContent())));
        }else if(type.equals("categorie")){
            Page pageCategorie = categorieBusiness.getPage(page, npp);
            paginationDTO.setNbpage(pageCategorie.getTotalPages());
            paginationDTO.setTotal(pageCategorie.getTotalElements());
            paginationDTO.setCategories(new ArrayList<>(CategorieTransformer.entityToDto(pageCategorie.getContent(), sousCat)));
            paginationDTO.setProduits(new ArrayList<>());
        }
        return paginationDTO;
    }
}
