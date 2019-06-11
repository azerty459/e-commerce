package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IPaginationBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.IUtilisateurBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PaginationDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.dto.transformer.CategorieTransformer;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.business.dto.transformer.UtilisateurTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service permettant de gérer les actions effectuées pour la pagination.
 */

@Service
public class PaginationBusiness implements IPaginationBusiness {

	@Autowired
	private IProduitBusiness produitBusiness;

	@Autowired
	private ICategorieBusiness categorieBusiness;

	@Autowired
	private IUtilisateurBusiness utilisateurBusiness;

	/**
	 * Retourne une liste paginée selon le type voulu, la page voulu et le nombre de produits à afficher.
	 *
	 * @param type         le type de la liste voulu
	 * @param pageActuelle la page souhaité
	 * @param npp          le nombre de produits à afficher dans la page paginée
	 * @return une objet PaginationDTO
	 */
	@Override
	public PaginationDTO getPagination(String type, int pageActuelle, int npp, String nom, int categorie, String nameOfTri) {

		if (pageActuelle <= 0) {
			pageActuelle = 1;
		}
		PaginationDTO paginationDTO;

		switch (type) {
			case "produit":
				paginationDTO = produit(pageActuelle, npp, nom, categorie, nameOfTri);
				break;
			case "categorie":
				Page pageCategorie = categorieBusiness.getPage(pageActuelle, npp);
				if (pageActuelle > pageCategorie.getTotalPages()) {
					pageCategorie = categorieBusiness.getPage(pageCategorie.getTotalPages(), npp);
				}
				paginationDTO = getPaginationDTO(pageCategorie, pageActuelle);
				paginationDTO.setCategories(new ArrayList<CategorieDTO>(CategorieTransformer.entityToDto(pageCategorie.getContent(), categorieBusiness.construireAssociationEnfantsChemins(pageCategorie.getContent()), false, false, null)));
				paginationDTO.setProduits(new ArrayList<>());
				break;
			case "utilisateur":
				Page utilisateurPage = utilisateurBusiness.getPage(pageActuelle, npp);
				if (pageActuelle > utilisateurPage.getTotalPages()) {
					utilisateurPage = utilisateurBusiness.getPage(utilisateurPage.getTotalPages(), npp);
				}
				paginationDTO = getPaginationDTO(utilisateurPage, pageActuelle);
				paginationDTO.setProduits(new ArrayList<>());
				paginationDTO.setCategories(new ArrayList<>());
				paginationDTO.setUtilisateurs(new ArrayList<UtilisateurDTO>(UtilisateurTransformer.entityToDto(utilisateurPage.getContent())));
				break;
			default:
				return null;

		}
		return paginationDTO;
	}

	private PaginationDTO produit(int pageActuelle, int npp, String nom, int categorie, String nameOfTri) {
		PaginationDTO paginationDTO;
		Page pageProduit = produitBusiness.getPage(pageActuelle, npp, nom, categorie);
		if (pageActuelle > pageProduit.getTotalPages()) {
			pageProduit = produitBusiness.getPage(pageProduit.getTotalPages(), npp, nom, categorie);
		}
		paginationDTO = getPaginationDTO(pageProduit, pageActuelle);
		paginationDTO.setCategories(new ArrayList<>());
		paginationDTO.setProduits(getProductOrderBy(new ArrayList<ProduitDTO>(ProduitTransformer.entityToDto(pageProduit.getContent())), nameOfTri));
		return paginationDTO;
	}

	private List<ProduitDTO> getProductOrderBy(List<ProduitDTO> produitDTOList, String nameOfTri) {
		List<ProduitDTO> orderedListProduitDTO;
		switch (nameOfTri) {
			case "Nom":
				orderedListProduitDTO = produitDTOList.stream().sorted(Comparator.comparing(ProduitDTO::getNom)).collect(Collectors.toList());
				break;
			case "Prix croissant":
				orderedListProduitDTO = produitDTOList.stream().sorted(Comparator.comparing(ProduitDTO::getPrixHT)).collect(Collectors.toList());
				break;
			default:
				orderedListProduitDTO = produitDTOList.stream().sorted(Comparator.comparing(ProduitDTO::getPrixHT).reversed()).collect(Collectors.toList());
		}
		return orderedListProduitDTO;
	}

	/**
	 * Méthode permettante de créer un objet PaginationDTO.
	 *
	 * @param page         Un objet de type page
	 * @param pageActuelle la page souhaité
	 * @return un objet PaginationDTO
	 */
	private PaginationDTO getPaginationDTO(Page page, int pageActuelle) {
		PaginationDTO paginationDTO = new PaginationDTO();
		paginationDTO.setPageMin(1);
		if (pageActuelle > page.getTotalPages()) {
			paginationDTO.setPageActuelle(page.getTotalPages());
		} else {
			paginationDTO.setPageActuelle(pageActuelle);
		}
		paginationDTO.setPageMax(page.getTotalPages());
		paginationDTO.setTotal(page.getTotalElements());
		return paginationDTO;
	}

}
