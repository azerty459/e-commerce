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
     * @param referenceProduit La référence du produit
     * @param description Description du produit
     * @param prixHT Prix hors taxe du produit
     * @return le produit créé
     */
    public Produit addProduit(String referenceProduit, String description, double prixHT) {
        return produitBusiness.addProduit(referenceProduit, description, prixHT);
    }

    /**
     * Modifie le produit.
     * @param referenceProduit Référence du produit à modifier
     * @param description La nouvelle description du produit
     * @param prixHT Le nouveau prix hors taxe du produit
     * @return le produit modifié
     */
    public Produit updateProduit(String referenceProduit, String description, double prixHT)  {
        return produitBusiness.updateProduit(referenceProduit, description, prixHT);
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
     * @param nomCategorie Nom de la catégorie
     * @param borneGauche La borne gauche de la catégorie
     * @param borneDroit La borne droit de la catégorie
     * @param level Level de la catégorie
     * @return la catégorie créé
     */
    public Categorie addCategorie(String nomCategorie, int borneGauche, int borneDroit, int level) {
        return categorieBusiness.addCategorie(nomCategorie, borneGauche, borneDroit, level);
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
