package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ProduitTransformer {

    private ProduitTransformer() {
    }

    /**
     * Transforme une collection d'objets ProduitDTO en une collection d'objets Produit.
     *
     * @param produitCollection Une collection d'objets Produit
     * @return une collection d'objet ProduitDTO
     */
    public static Collection<ProduitDTO> listEntityToDto(Collection<Produit> produitCollection) {
        if (produitCollection == null) {
            return null;
        }
        List<ProduitDTO> produitDTOCollection = new ArrayList<>();
        for (Produit produit : produitCollection) {
            produitDTOCollection.add(entityToDto(produit));
        }
        return produitDTOCollection;
    }

    /**
     * Transforme un objet Produit en ProduitDTO
     *
     * @param produit Un objet Produit
     * @return une objet ProduitDTO
     */
    public static ProduitDTO entityToDto(Produit produit) {
        if (produit == null) {
            return null;
        }
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setRef(produit.getReferenceProduit());
        produitDTO.setNom(produit.getNom());
        produitDTO.setDescription(produit.getDescription());
        produitDTO.setPrixHT(produit.getPrixHT());
        produitDTO.setPhotos(new ArrayList<>(PhotoTransformer.entityToDto(produit.getPhotos())));
        if (produit.getPhotoPrincipale() != null) {
            produitDTO.setPhotoPrincipale(PhotoTransformer.entityToDto(produit.getPhotoPrincipale()));
        }

        // Ajout du paramètre de chemins (US#192)
        HashMap<Categorie, Collection<Categorie>> chemins = new HashMap<>();
        produitDTO.setCategories(new ArrayList<>(CategorieTransformer.entityToDto(produit.getCategories(), chemins, false, false, null)));

        //Add characteristics Entity -> DTO
        produitDTO.setCharacteristicDTOList(CharacteristicTransformer.listEntityToDto(produit.getCharacteristicList()));

        return produitDTO;
    }

    /**
     * Transforme une collection d'objets Produit en une collection d'objets ProduitDTO.
     *
     * @param produitDTOCollection Une collection d'objets ProduitDTO
     * @return une collection d'objet Produit
     */
    public static Collection<Produit> listDtoToEntity(Collection<ProduitDTO> produitDTOCollection) {
        if (produitDTOCollection == null) {
            return null;
        }
        List<Produit> produits = new ArrayList<>();
        for (ProduitDTO produitDTO : produitDTOCollection) {
            produits.add(dtoToEntity(produitDTO));
        }
        return produits;
    }

    /**
     * Transforme un objet ProduitDTO en Produit
     *
     * @param produitDTO Un objet ProduitDTO
     * @return une objet Produit
     */
    public static Produit dtoToEntity(ProduitDTO produitDTO) {
        if (produitDTO == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setReferenceProduit(produitDTO.getRef());
        produit.setNom(produitDTO.getNom());
        produit.setDescription(produitDTO.getDescription());
        produit.setPrixHT(produitDTO.getPrixHT());
        produit.setPhotos(new ArrayList<>(PhotoTransformer.dtoToEntity(produitDTO.getPhotos())));
        produit.setCategories(new ArrayList<>(CategorieTransformer.dtoToEntity(produitDTO.getCategories())));
        produit.setPhotoPrincipale(PhotoTransformer.dtoToEntity(produitDTO.getPhotoPrincipale()));

        //Add characteristics DTO -> Entity
        produit.setCharacteristicList(CharacteristicTransformer.listDtoToEntity(produitDTO.getCharacteristicDTOList()));

        return produit;
    }
}
