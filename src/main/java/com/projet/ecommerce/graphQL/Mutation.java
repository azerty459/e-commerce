package com.projet.ecommerce.graphQL;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.entity.Categorie;
import com.projet.ecommerce.entity.Produit;
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
     * @param produit Un objet de type Produit
     * @return le produit créé
     */
    public Produit addProduit(Produit produit) {
        return produitBusiness.addProduit(produit);
    }

    /**
     * Modifie le produit.
     * @param produit Un objet de type Produit
     * @return le produit modifié
     */
    public Produit updateProduit(Produit produit)  {
        return produitBusiness.updateProduit(produit);
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
     * Ajouter une catégorie.
     * @param categorie Un objet de type catégorie
     * @return la catégorie créé
     */
    public Categorie addCategorie(Categorie categorie) {
        return categorieBusiness.addCategorie(categorie);
    }

    /**
     * Supprime une catégorie
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    public boolean deleteCategorie(String nomCategorie) {
        return categorieBusiness.deleteCategorie(nomCategorie);
    }
}
