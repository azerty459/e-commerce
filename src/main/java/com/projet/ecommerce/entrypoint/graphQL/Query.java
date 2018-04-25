package com.projet.ecommerce.entrypoint.graphQL;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
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
     * Implémente la query GraphQL "produits"
     *
     * @param ref la référence du produit recherché
     * @param cat la catégorie du /des produit(s) recherché(s)
     * @return la liste des produits trouvés selon ces critères
     */
    public List<ProduitDTO> produits(String ref, String cat) {

        return produitBusiness.getAll(ref, cat);
    }

}