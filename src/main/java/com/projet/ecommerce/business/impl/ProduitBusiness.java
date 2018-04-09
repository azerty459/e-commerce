package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitBusiness implements IProduitBusiness {

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Ajoute un produit dans la base de données.
     * @param produitDTO Objet ProduitDTO
     * @return l'objet produit crée
     */
    @Override
    public ProduitDTO addProduit(ProduitDTO produitDTO) {
        produitRepository.save(ProduitTransformer.dtoToEntity(produitDTO));
        return produitDTO;
    }

    /**
     * Modifie le produit dans la base de données
     * @param produitDTO Objet ProduitDTO
     * @return l'objet produit modifié, null le produit à modifier n'est pas trouvée
     */
    @Override
    public ProduitDTO updateProduit(ProduitDTO produitDTO) {
        produitRepository.save(ProduitTransformer.dtoToEntity(produitDTO));
        return produitDTO;
    }

    /**
     * Supprime le produit dans la base de données.
     * @param referenceProduit Référence du produit à supprimer
     * @return true
     */
    @Override
    public boolean deleteProduit(String referenceProduit) {
        Optional<Produit> produit = produitRepository.findById(referenceProduit);
        if(produit.isPresent()){
            produitRepository.delete(produit.get());
            return true;
        }
        return false;
    }

    /**
     * Retourne la liste complète des produits liés à la base de données.
     * @return une liste de produit
     */
    @Override
    public List<ProduitDTO> getProduit() {
        return ProduitTransformer.entityToDTO(new ArrayList<>(produitRepository.findAll()));
    }

    /**
     * Retourne le produit recherché.
     * @param referenceProduit Référence du produit à rechercher
     * @return l'objet produit recherché sinon null, s'il n'est pas trouvé
     */
    @Override
    public ProduitDTO getProduitByID(String referenceProduit) {
        Optional<Produit> produit = produitRepository.findById(referenceProduit);
        return ProduitTransformer.entityToDTO(produit.orElse(null));
    }
}
