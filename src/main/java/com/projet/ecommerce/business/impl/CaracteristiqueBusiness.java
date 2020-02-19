package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Libelle;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.LibelleRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

//XXX - Transactional : org.javax ou org.springframework?
//XXX - Transactional : pourquoi ?
@Service
@Transactional
public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    LibelleRepository libelleRepository;
    @Autowired
    CaracteristiqueRepository caracteristiqueRepository;

    @Override
    public CaracteristiqueDTO addCaracteristique(String refProduit, int idLibelle, String valeurCaracteristique) {
        //1.Validation : valeurs null -> null, si produit ou libelle non trouvé -> erreur
        //  si deja une caracteristique pour ce produit et libelle -> erreur
        //  si valeurCaracteristique vide (ou contient que espaces) -> erreur

        //XXX - comme dans par exemple UtilisateurBusiness
        //pourquoi pas erreur?
        if (refProduit == null || valeurCaracteristique == null) return null;

        //XXX - peut être penser à standardiser messages d'erreurs et les stocker tous dans un seul endroit
        Produit produit = produitRepository.findById(refProduit).orElseThrow(
                () -> new GraphQLCustomException("Pas de produit trouvé pour la référence : " + refProduit));
        Libelle libelle = libelleRepository.findById(idLibelle).orElseThrow(
                () -> new GraphQLCustomException("Pas de libellé trouvé pour l'id : " + idLibelle));

        Caracteristique caracteristiqueFound = null;
        boolean erreurDeclenchee = false;
        try {
            caracteristiqueFound = getCaracteristiqueEntity(refProduit,idLibelle);
        } catch (Exception e) {
            erreurDeclenchee = true;
        }
        if (!erreurDeclenchee && caracteristiqueFound != null) throw new GraphQLCustomException(
                "Une caractéristique existe déja pour le produit : " + caracteristiqueFound.getProduit().getReferenceProduit() +
                " et le libellé : " + caracteristiqueFound.getLibelle().getNom());

        valeurCaracteristique = valeurCaracteristique.trim();
        if (valeurCaracteristique.length()==0) throw new GraphQLCustomException(
                "La valeur d'une caractéristique ne peut être vide");

        //2.Exécution
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setProduit(produit);
        caracteristique.setLibelle(libelle);
        caracteristique.setValeur(valeurCaracteristique);
        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }

    //XXX - eventuellement addCaracteristiqueWithNewLibelle méthode...

    @Override
    public Collection<CaracteristiqueDTO> getAllCaracteristiques(String refProduit) {
        //XXX - comme dans par exemple UtilisateurBusiness
        //pourquoi pas erreur?
        if (refProduit == null) return null;
        Produit produit = produitRepository.findById(refProduit).orElseThrow(
                () -> new GraphQLCustomException("Pas de produit trouvé pour la référence : " + refProduit));
        Collection<Caracteristique> caracteristiques = caracteristiqueRepository.findByProduit(produit);
        return CaracteristiqueTransformer.entityToDto(caracteristiques);
    }

    @Override
    public Collection<CaracteristiqueDTO> getAllCaracteristique(String refProduit, String motCle) {
        if (refProduit == null || motCle==null) return null;
        Produit produit = produitRepository.findById(refProduit).orElseThrow(
                () -> new GraphQLCustomException("Pas de produit trouvé pour la référence : " + refProduit));
        Collection<Caracteristique> caracteristiques = caracteristiqueRepository.
            findByProduitAndMotCle(produit,motCle);
        return CaracteristiqueTransformer.entityToDto(caracteristiques);
    }

    @Override
    public CaracteristiqueDTO getCaracteristique(String refProduit, int idLibelle) {
        return CaracteristiqueTransformer.entityToDto(getCaracteristiqueEntity(refProduit,idLibelle));
    }

    @Override
    public CaracteristiqueDTO updateCaracteristique(String refProduit, int idLibelle, String nouvelleValeur) {
        //1.Validation
        nouvelleValeur = nouvelleValeur.trim();
        if (nouvelleValeur.length()==0) throw new GraphQLCustomException(
                "La valeur d'une caractéristique ne peut être vide");

        //2.Exécution
        Caracteristique caracteristique = getCaracteristiqueEntity(refProduit,idLibelle);
        caracteristique.setValeur(nouvelleValeur);
        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }

    @Override
    public void deleteCaracteristique(String refProduit, int idLibelle) {
        Caracteristique caracteristique = getCaracteristiqueEntity(refProduit,idLibelle);
        caracteristiqueRepository.delete(caracteristique);
    }



    //méthode privée pour récupérer une caractéristique sous forme d'entité
    private Caracteristique getCaracteristiqueEntity(String refProduit, int idLibelle) {
        //On cherche le produit et le libellé
        if (refProduit == null) return null;
        Produit produit = produitRepository.findById(refProduit).orElseThrow(
                () -> new GraphQLCustomException("Pas de produit trouvé pour la référence : " + refProduit));
        Libelle libelle = libelleRepository.findById(idLibelle).orElseThrow(
                () -> new GraphQLCustomException("Pas de libellé trouvé pour l'id : " + idLibelle));

        //On cherche la caractéristique
        Collection<Caracteristique> caracteristiques = caracteristiqueRepository.
                findByProduitAndLibelle(produit,libelle);

        //3 cas possibles (normalement 2) : on trouve 0, plusieurs, ou exactement 1 caractéristique
        if (caracteristiques.size()==0) throw new GraphQLCustomException(
                "Pas de caractéristique trouvée pour le produit : " + produit.getNom() +
                        " et le libellé : " + libelle.getNom());
        if (caracteristiques.size()>1) throw new GraphQLCustomException(
                "Erreur de programmation : plusieurs caractéristiques pour un même (produit, libellé)");
        return caracteristiques.iterator().next();
    }
}
