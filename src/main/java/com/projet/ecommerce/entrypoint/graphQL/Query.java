package com.projet.ecommerce.entrypoint.graphQL;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
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

    public List<ProduitDTO> getAllProduit() {
        return produitBusiness.getProduit();
    }

    public ProduitDTO getProduitByID(String referenceProduit) {
        return produitBusiness.getProduitByID(referenceProduit);
    }

    public List<CategorieDTO> getAllCategorie() {
        System.out.println("nom: "+categorieBusiness.getCategorie().get(0).getNomCategorie());
        return categorieBusiness.getCategorie();
    }

    public CategorieDTO getCategorieByID(String nomCategorie) {
        return categorieBusiness.getCategorieByID(nomCategorie);
    }
}