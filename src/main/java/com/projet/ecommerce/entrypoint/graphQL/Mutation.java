package com.projet.ecommerce.entrypoint.graphQL;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class permettant de définir les méthodes à utiliser pour la Mutation de graphQL.
 */
@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private IProduitBusiness produitBusiness;

    @Autowired
    private ICategorieBusiness categorieBusiness;

    /**
     * Ajoute un produit.
     * @param referenceProduit La référence du produit
     * @param nom Le nom du produit
     * @param description Sa description
     * @param prixHT Son prix hors taxe
     * @return le produit ajouté
     */
    public ProduitDTO addProduit(String referenceProduit, String nom, String description, Float prixHT) {
        return produitBusiness.addProduit(referenceProduit, nom, description, prixHT);
    }

    /**
     * Modifie le produit
     * @param referenceProduit La référence du produit à modifier
     * @param nom Le nouveau nom
     * @param description La nouvelle description
     * @param prixHT Le nouveau prix hors taxe
     * @return le produit modifié
     */
    public ProduitDTO updateProduit(String referenceProduit, String nom, String description, Float prixHT)  {
        return produitBusiness.updateProduit(referenceProduit, nom, description, prixHT);
    }

    /**
     * Supprime un produit.
     * @param referenceProduit Référence du produit à supprimer
     * @return true
     */
    public boolean deleteProduit(String referenceProduit) {
        return produitBusiness.deleteProduit(referenceProduit);
    }

    /**
     * Ajoute une catégorie.
     * @param nomCategorie Le nom de la catégorie
     * @return la catégorie crée
     */
    public CategorieDTO addCategorie(String nomCategorie, String pere){
        return  categorieBusiness.addCategorie(nomCategorie, pere);
    }

    /**
     * Supprime une catégorie.
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    public boolean deleteCategorie(String nomCategorie){
        return categorieBusiness.deleteCategorie(nomCategorie);
    }
}
