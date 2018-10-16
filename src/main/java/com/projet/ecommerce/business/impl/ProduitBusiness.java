package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.projet.ecommerce.utilitaire.Utilitaire.mergeObjects;

/**
 * Service permettant de gérer les actions effectuées pour les produits.
 */

@Service
public class ProduitBusiness implements IProduitBusiness {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private PhotoRepository photoRepository;

    /**
     * Ajoute un produit dans la base de données.
     *
     * @param referenceProduit  La référence du produit
     * @param nom               Le nom du produit
     * @param description       Sa description
     * @param prixHT            Son prix hors taxe
     * @param categoriesProduit Liste d'id de catégorie à associer au produit
     * @return l'objet produit crée ou null, s'il il manque une referenceProduit, un nom et un prixHT.
     */
    @Override
    public ProduitDTO add(String referenceProduit, String nom, String description, float prixHT, List<Integer> categoriesProduit) {
        if (referenceProduit.isEmpty() && nom.isEmpty()) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout du produit (la référence, le nom et le prixHT ne peut être null)");
            graphQLCustomException.ajouterExtension("Référence", referenceProduit);
            graphQLCustomException.ajouterExtension("Nom", nom);
            graphQLCustomException.ajouterExtension("PrixHT", prixHT + "");
            throw graphQLCustomException;
        }
        if (produitRepository.findById(referenceProduit).isPresent()) {
            throw new GraphQLCustomException("Le produit à ajouter existe déjà.");
        }
        Produit produit = new Produit();
        produit.setReferenceProduit(referenceProduit);
        produit.setNom(nom);
        produit.setDescription(description);
        produit.setPrixHT(prixHT);
        produit.setPhotos(new ArrayList<>());

        List<Categorie> categorieList = new ArrayList<>();
        if (categoriesProduit != null) {
            for (int idCategorie : categoriesProduit) {
                Optional<Categorie> categorieOptional = categorieRepository.findById(idCategorie);
                categorieOptional
                        .map(categorieList::add);
            }
        }
        produit.setCategories(categorieList);
        return ProduitTransformer.entityToDto(produitRepository.save(produit));
    }

    /**
     * Modifie le produitDTO dans la base de données.
     *
     * @param produitDTO L'objet produitDTO modifié à sauvegarder
     * @return l'objet produitDTO modifié
     */
    @Override
    public ProduitDTO update(ProduitDTO produitDTO) {
        Produit produit = ProduitTransformer.dtoToEntity(produitDTO);
        if (produit == null) {
            throw new GraphQLCustomException("Le produit envoyé est invalide.");
        }
        Optional<Produit> produitOptional = produitRepository.findById(produit.getReferenceProduit());
        if (!produitOptional.isPresent()) {
            throw new GraphQLCustomException("Le produit recherché n'existe pas.");
        }
        return ProduitTransformer.entityToDto(produitRepository.save(produit));
    }

    /**
     * Remplace la liste de photo par une nouvelle liste contenant l'ensemble des données des photos.
     *
     * @param photoList Une array list contenant des id de photo
     * @return une collection de Photo
     */
    private Collection<Photo> completePhotosData(List<Photo> photoList) {
        List<Photo> retourList = new ArrayList<>();
        for (Photo photo : photoList) {
            Optional<Photo> photoOptional = photoRepository.findById(photo.getIdPhoto());
            if (photoOptional.isPresent()) {
                retourList.add(photoOptional.get());
            } else {
                throw new GraphQLCustomException("La photo n'existe pas.");
            }
        }
        return retourList;
    }

    /**
     * Remplace la liste de photo par une nouvelle liste contenant l'ensemble des données des photos.
     *
     * @param photoPrincipale La photo principale
     * @return une collection de Photo
     */
    private Photo completePhotoPrincipaleData(Photo photoPrincipale) {
        Optional<Photo> photoOptional = photoRepository.findById(photoPrincipale.getIdPhoto());
        if (photoOptional.isPresent()) {
            return photoOptional.get();
        } else {
            throw new GraphQLCustomException("La photo n'existe pas.");
        }
    }

    /**
     * Remplace la liste de caractéristique par une nouvelle liste contenant l'ensemble des données des caractéristiques.
     *
     * @param categorieList Une array list contenant des id de catégorie
     * @return une collection de catégorie
     */
    private Collection<Categorie> completeCategoriesData(List<Categorie> categorieList) {
        List<Categorie> retourList = new ArrayList<>();
        for (Categorie categorie : categorieList) {
            Optional<Categorie> categorieOptional = categorieRepository.findById(categorie.getIdCategorie());
            if (categorieOptional.isPresent()) {
                retourList.add(categorieOptional.get());
            } else {
                throw new GraphQLCustomException("La catégorie n'existe pas.");
            }
        }
        return retourList;
    }

    /**
     * Supprime le produit dans la base de données.
     *
     * @param referenceProduit Référence du produit à supprimer
     * @return true
     */
    @Override
    public boolean delete(String referenceProduit) {
        produitRepository.deleteById(referenceProduit);
        return true;
    }

    /**
     * Retourne la liste complète des produits liés à la base de données.
     *
     * @return une liste d'objet produit
     */
    @Override
    public List<ProduitDTO> getAll() {
        return new ArrayList<>(ProduitTransformer.entityToDto(new ArrayList<>(produitRepository.findAll())));
    }

    /**
     * Va chercher les produits selon les paramètres ci-dessous
     *
     * @param ref la référence du produit recherché
     * @param cat la catégorie du /des produit(s) recherché(s)
     * @param nom le nom du produit à rechercher
     * @return une liste de produits selon les paramètres
     */
    @Override
    public List<ProduitDTO> getAll(String ref, String cat, String nom) {

        Collection<Produit> produitCollection;

        if (nom == null) {
            produitCollection = produitRepository.findAllWithCriteria(ref, cat);

        } else {
            produitCollection = produitRepository.findByNomContainingIgnoreCase(nom);
        }

        if (produitCollection.size() == 0) {
            throw new GraphQLCustomException("Aucun produit(s) trouvé(s).");
        }
        List<ProduitDTO> result = new ArrayList<>(ProduitTransformer.entityToDto(new ArrayList<>(produitCollection)));
        return result;
    }

    /**
     * Retourne une page de produit
     *
     * @param numeroPage    la page souhaitée
     * @param nombreProduit le nombre de produit à afficher dans la
     * @param nom           le nom du produit à rechercher
     * @param IDcategorie   l'id de la catégorie recherchée
     * @return une page paginée
     */
    @Override
    public Page<Produit> getPage(int numeroPage, int nombreProduit, String nom, int IDcategorie) {


        PageRequest page = (numeroPage == 0) ? PageRequest.of(numeroPage, nombreProduit) : PageRequest.of(numeroPage - 1, nombreProduit);
        if (nom != null && !nom.isEmpty() && IDcategorie != 0 || IDcategorie != 0) {
            Optional<Categorie> categorieOptional = categorieRepository.findById(IDcategorie);
            if (!categorieOptional.isPresent()) {
                return Page.empty();
            }
            Categorie categorie = categorieOptional.get();
            return produitRepository.findByNomContainingIgnoreCaseAndCategories_borneGaucheGreaterThanEqualAndCategories_borneDroitLessThanEqual(page, nom, categorie.getBorneGauche(), categorie.getBorneDroit());
        } else if (nom != null && !nom.isEmpty()) {
            return produitRepository.findByNomContainingIgnoreCase(page, nom);
        }
        return produitRepository.findAll(page);
    }
}
