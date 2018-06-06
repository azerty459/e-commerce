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
     *
     * @param type le type de la liste voulu
     * @param pageActuelle la page souhaité
     * @param npp  le nombre de produits à afficher dans la page paginée
     * @return une objet PaginationDTO
     */
    @Override
    public PaginationDTO getPagination(String type, int pageActuelle, int npp, String nom) {

        if (pageActuelle <= 0) {
            pageActuelle = 1;
        }

        PaginationDTO paginationDTO = null;

        switch (type) {
            case "produit":
                Page pageProduit = produitBusiness.getPage(pageActuelle, npp, nom);
                if (pageActuelle > pageProduit.getTotalPages()) {
                    pageProduit = produitBusiness.getPage(pageProduit.getTotalPages(), npp, nom);
                }
                paginationDTO = getPaginationDTO(pageProduit, pageActuelle);
                paginationDTO.setCategories(new ArrayList<>());
                paginationDTO.setProduits(new ArrayList<>(ProduitTransformer.entityToDto(pageProduit.getContent())));
                break;
            case "categorie":
                Page pageCategorie = categorieBusiness.getPage(pageActuelle, npp);
                if (pageActuelle > pageCategorie.getTotalPages()) {
                    pageCategorie = categorieBusiness.getPage(pageCategorie.getTotalPages(), npp);
                }
                paginationDTO = getPaginationDTO(pageCategorie, pageActuelle);
                paginationDTO.setCategories(new ArrayList<>(CategorieTransformer.entityToDto(pageCategorie.getContent(), this.categorieBusiness.construireAssociationEnfantsChemins(pageCategorie.getContent()), false, false, null)));
                paginationDTO.setProduits(new ArrayList<>());
                break;
            default:
                return paginationDTO;

        }
        return paginationDTO;
    }

    /**
     * Méthode permettante de créer un objet PaginationDTO.
     * @param page Un objet de type page
     * @param pageActuelle la page souhaité
     * @returnun objet PaginationDTO
     */
    private PaginationDTO getPaginationDTO(Page page, int pageActuelle) {
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPageMin(1);
        if(pageActuelle > page.getTotalPages()){
            paginationDTO.setPageActuelle(page.getTotalPages());
        }else{
            paginationDTO.setPageActuelle(pageActuelle);
        }
        paginationDTO.setPageMax(page.getTotalPages());
        paginationDTO.setTotal(page.getTotalElements());
        return paginationDTO;
    }
}
