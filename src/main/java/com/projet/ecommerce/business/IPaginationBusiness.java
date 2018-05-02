package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.PaginationDTO;

import java.util.List;

/**
 * Interface du service PaginationBusiness.
 */

public interface IPaginationBusiness {

    /**
     * Méthode définissant la pagination d'une liste.
     * @param type le type de la liste voulu
     * @param page la page souhaitée
     * @param npp le nombre de produits à afficher dans la page paginée
     * @return une objet PaginationDTO
     */
    PaginationDTO getPagination(String type, int page, int npp);
}
