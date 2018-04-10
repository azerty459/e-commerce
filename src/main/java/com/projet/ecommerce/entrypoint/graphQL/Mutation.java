package com.projet.ecommerce.entrypoint.graphQL;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
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
     * @param produitDTO Un objet de type Produit
     * @return le produit créé
     */
    public ProduitDTO addProduit(ProduitDTO produitDTO) {
        return produitBusiness.addProduit(produitDTO);
    }

    /**
     * Modifie le produit.
     * @param produitDTO Un objet de type ProduitDTO
     * @return le produit modifié
     */
    public ProduitDTO updateProduit(ProduitDTO produitDTO)  {
        return produitBusiness.updateProduit(produitDTO);
    }

    /**
     * Supprime un produit.
     * @param referenceProduit Référence du produit à supprimer
     * @return true
     */
    public boolean deleteProduit(String referenceProduit) {
        return produitBusiness.deleteProduit(referenceProduit);
    }
}
