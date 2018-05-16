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
    public PaginationDTO getPagination(String type, int page, int npp) {
        PaginationDTO paginationDTO = new PaginationDTO();
        if(page < 0) {
            page = 1;
        }
        if(type.equals("produit")){
            Page pageProduit = produitBusiness.getPage(page, npp);
            paginationDTO.setPageMin(1);
            paginationDTO.setPageActuelle(page);
            if(page > pageProduit.getTotalPages()){
                pageProduit = produitBusiness.getPage(pageProduit.getTotalPages(), npp);
                paginationDTO.setPageActuelle(pageProduit.getTotalPages());
            }
            paginationDTO.setPageMax(pageProduit.getTotalPages());
            paginationDTO.setTotal(pageProduit.getTotalElements());
            paginationDTO.setCategories(new ArrayList<>());
            paginationDTO.setProduits(new ArrayList<>(ProduitTransformer.entityToDto(pageProduit.getContent())));
        }else if(type.equals("categorie")){
            Page pageCategorie = categorieBusiness.getPage(page, npp);
            paginationDTO.setPageMin(1);
            paginationDTO.setPageActuelle(page);
            if(page > pageCategorie.getTotalPages()){
                pageCategorie = categorieBusiness.getPage(pageCategorie.getTotalPages(), npp);
                paginationDTO.setPageActuelle(pageCategorie.getTotalPages());
            }
            paginationDTO.setPageMax(pageCategorie.getTotalPages());
            paginationDTO.setTotal(pageCategorie.getTotalElements());


            paginationDTO.setCategories(new ArrayList<>(CategorieTransformer.entityToDto(pageCategorie.getContent(), this.categorieBusiness.construireAssociationEnfantsChemins(pageCategorie.getContent()), false)));
            paginationDTO.setProduits(new ArrayList<>());
        }
        return paginationDTO;
    }
}
