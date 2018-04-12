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
 * Class permettant de définir les méthodes à utiliser pour les Query de graphQL.
 */
@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private ICategorieBusiness categorieBusiness;

    @Autowired
    private IProduitBusiness produitBusiness;

    public List<ProduitDTO> getAllProduit() {
        return produitBusiness.getAll();
    }

    public ProduitDTO getProduitByRef(String referenceProduit) {
        return produitBusiness.getByRef(referenceProduit);
    }

    public List<ProduitDTO> getProduitByCategorie(String nomCategorie){
        return produitBusiness.getByCategorie(nomCategorie);
    }

    public List<CategorieDTO> getAllCategorie() {
        return categorieBusiness.getAll();
    }

    public CategorieDTO getCategorieByNom(String nomCategorie) {
        return categorieBusiness.getByNom(nomCategorie);
    }
}