package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.ArrayList;
import java.util.List;

public class ProduitTransformer {

    public static List<ProduitDTO> entityToDTO(List<Produit> produitCollection){
        if (produitCollection == null) return null;
        List<ProduitDTO> produitDTOCollection = new ArrayList<>();
        for (Produit produit : produitCollection) {
            produitDTOCollection.add(entityToDTO(produit));
        }
        return produitDTOCollection;
    }

    public static ProduitDTO entityToDTO(Produit produit){
        if(produit == null) return null;
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setReferenceProduit(produit.getReferenceProduit());
        produitDTO.setNom(produit.getNom());
        produitDTO.setDescription(produit.getDescription());
        produitDTO.setPrixHT(produit.getPrixHT());
        produitDTO.setCaracteristiques(CaracteristiqueTransformer.entityToDto(produit.getCaracteristiques()));
        produitDTO.setPhotos(PhotoTransformer.entityToDto(produit.getPhotos()));
        produitDTO.setCategories(CategorieTransformer.entityToDto(produit.getCategories()));
        return produitDTO;
    }

    public static  List<Produit> dtoToEntity(List<ProduitDTO> produitDTOCollection){
        if(produitDTOCollection == null) return null;
        List<Produit> produits = new ArrayList<>();
        for (ProduitDTO produitDTO : produitDTOCollection) {
            produits.add(dtoToEntity(produitDTO));
        }
        return produits;
    }

    public static Produit dtoToEntity(ProduitDTO produitDTO){
        if(produitDTO == null) return null;
        Produit produit = new Produit();
        produit.setReferenceProduit(produitDTO.getReferenceProduit());
        produit.setNom(produitDTO.getNom());
        produit.setDescription(produitDTO.getDescription());
        produit.setPrixHT(produitDTO.getPrixHT());
        produit.setCaracteristiques(CaracteristiqueTransformer.dtoToEntity(produitDTO.getCaracteristiques()));
        produit.setPhotos(PhotoTransformer.dtoToEntity(produitDTO.getPhotos()));
        produit.setCategories(CategorieTransformer.dtoToEntity(produitDTO.getCategories()));
        return produit;
    }
}
