package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface du service ProduitBusiness.
 */

//XXX - pourquoi pas methodes statiques ?
public interface IProduitBusiness {

    ProduitDTO add(String referenceProduit, String nom, String description, float prixHT, List<Integer> nouvelleCat);
    ProduitDTO update(Produit produit);
    boolean delete(String referenceProduit);
    List<ProduitDTO> getAll();
    List<ProduitDTO> getAll(String ref, String nom, String cat);

    /**
     * Méthode définissant la pagination
     *
     * @param pageNumber  la page souhaitée
     * @param nb          le nombre de produit à afficher dans la page
     * @param nom         le nom du produit recherché
     * @param IDcategorie l'id catégorie recherchée
     * @return une page de produit
     */
    //XXX - ???
    Page<Produit> getPage(int pageNumber, int nb, String nom, int IDcategorie);
}
