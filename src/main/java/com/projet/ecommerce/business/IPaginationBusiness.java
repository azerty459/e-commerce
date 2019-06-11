package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.PaginationDTO;

/**
 * Interface du service PaginationBusiness.
 */

public interface IPaginationBusiness {

	/**
	 * Méthode définissant la pagination d'une liste.
	 *
	 * @param type        le type de la liste voulu
	 * @param page        la page souhaitée
	 * @param npp         le nombre de produits à afficher dans la page paginée
	 * @param nom         le nom du produit recherché
	 * @param IDcategorie l'id de la catégorie recherché
	 * @return une objet PaginationDTO
	 */
	PaginationDTO getPagination(String type, int page, int npp, String nom, int IDcategorie, String nameOfTri);

}
