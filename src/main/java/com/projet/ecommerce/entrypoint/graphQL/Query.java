package com.projet.ecommerce.entrypoint.graphQL;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe implémentant les méthodes de requête de graphQL.
 */
@Component
public class Query implements GraphQLQueryResolver {

    /**
     *
     */
    @Autowired
    private ICategorieBusiness categorieBusiness;

    @Autowired
    private IProduitBusiness produitBusiness;

    /**
     * Obtenir tous les produits présents en base
     * @return la liste des produits présents en base
     */
    public List<ProduitDTO> produit() {
        return produitBusiness.getAll();
    }

    /**
     * Obtenir un produit en fonction de sa référence
     * @param referenceProduit la référence du produit voulu
     * @return le produit recherché
     */
    public ProduitDTO getProduitByRef(String referenceProduit) {
        return produitBusiness.getByRef(referenceProduit);
    }

    /**
     * Obtenir les produits appartenant à une catégorie
     * @param nomCategorie la catégorie voulue
     * @return la liste des produits appartenants à la catégorie recherchée
     */
    public List<ProduitDTO> getProduitByCategorie(String nomCategorie){
        return produitBusiness.getByCategorie(nomCategorie);
    }

    /**
     * Obtenir toute les catégories présentes en base
     * @return la liste des catégories présentes en base
     */
    public List<CategorieDTO> getAllCategorie() {
        return categorieBusiness.getAll();
    }

    /**
     * Obtenir une catégorie en fonction de son nom
     * @param nomCategorie le nom de la catégorie recherchée
     * @return la catégorie trouvée
     */
    public CategorieDTO getCategorieByNom(String nomCategorie) {
        return categorieBusiness.getByNom(nomCategorie);
    }
}