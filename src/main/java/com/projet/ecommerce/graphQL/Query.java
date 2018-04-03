package com.projet.ecommerce.graphQL;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.entity.Categorie;
import com.projet.ecommerce.entity.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class permettant de définir les méthodes à utiliser pour les Query de graphQL.
 */
@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private ICategorieBusiness categorieBusiness;

    @Autowired
    private IProduitBusiness produitBusiness;

    /**
     * Retourne une liste contenant toutes les catégories liés à la table catégorie.
     * @return une liste de catégories
     */
    public List<Categorie> getCategorie() {
        return categorieBusiness.getCategorie();
    }

    /**
     * Retourne une liste contenant tous les produits liés à la table produit.
     * @return une liste de produits
     */
    public List<Produit> getProduit() {
        return produitBusiness.getProduit();
    }

    /**
     * Retourne la catégorie possédant le nom mit en paramètre.
     * @param nom Le nom de la catégorie recherché.
     * @return la catégorie recherchée
     */
    public Categorie getCategorieByID(String nom) {
        return categorieBusiness.getCategorieByID(nom);
    }

    /**
     * Retourne le produit possédant la référence du produit mit en paramètre.
     * @param referenceProduit La référence du produit recherché.
     * @return le produit recherché
     */
    public Produit getProduitByID(String referenceProduit) {
        return produitBusiness.getProduitByID(referenceProduit);
    }
}