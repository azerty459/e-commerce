package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.StatistiqueProduitCategorieDTO;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.AvisClientRepository;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private AvisClientRepository avisClientRepository;

    /**
     * Ajoute un produit dans la base de données.
     *
     * @param referenceProduit  La référence du produit
     * @param nom               Le nom du produit
     * @param description       Sa description
     * @param prixHT            Son prix hors taxe
     * @param categoriesProduit Liste d'id de catégorie à associer au produit
     * @return l'objet produit crée ou null, s'il il manque une referenceProduit, un
     * nom et un prixHT.
     */
    @Override
    // FIXME à remplacer par un DTO
    public ProduitDTO add(String referenceProduit, String nom, String description, float prixHT,
                          List<Integer> categoriesProduit) {
        if (referenceProduit.isEmpty() && nom.isEmpty()) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException(
                    "Erreur dans l'ajout du produit (la référence, le nom et le prixHT ne peut être null)");
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
                categorieOptional.map(categorieList::add);
            }
        }
        produit.setCategories(categorieList);
        return ProduitTransformer.entityToDto(produitRepository.save(produit));
    }

    /**
     * Modifie le produit dans la base de données.
     *
     * @param produit L'objet produit modifié à sauvegarder
     * @return l'objet produit modifié
     */
    // Todo changer parametre par ProduitDTO
    @Override
    public ProduitDTO update(Produit produit) {
        if (produit == null) {
            return null;
        }
        Optional<Produit> produitOptional = produitRepository.findById(produit.getReferenceProduit());
        if (!produitOptional.isPresent()) {
            throw new GraphQLCustomException("Le produit recherché n'existe pas.");
        }

        // On fusionne les deux produits en un
        Produit retourProduit = produitOptional.get();

        produit.setCategories(new ArrayList<>(completeCategoriesData(produit.getCategories())));
        if (produit.getPhotoPrincipale() != null && produit.getPhotoPrincipale().getIdPhoto() != 0) {
            produit.setPhotoPrincipale(completePhotoPrincipaleData(produit.getPhotoPrincipale()));
        }
        Produit produitFinal = null;
        try {
            produitFinal = mergeObjects(produit, retourProduit);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        if (produitFinal == null) {
            throw new GraphQLCustomException("Le produit ne peux pas être sauvegardé");
        }

        // On retourne le produit final et on le transforme en DTO
        return ProduitTransformer.entityToDto(produitRepository.save(produitFinal));
    }

    /**
     * Remplace la liste de photo par une nouvelle liste contenant l'ensemble des
     * données des photos.
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
     * Remplace la liste de photo par une nouvelle liste contenant l'ensemble des
     * données des photos.
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
     * Remplace la liste de caractéristique par une nouvelle liste contenant
     * l'ensemble des données des caractéristiques.
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

        Collection<Produit> produitCollection = null;

        if (nom == null) {
            produitCollection = produitRepository.findAllWithCriteria(ref, cat);

        } else {
            produitCollection = produitRepository.findByNomContainingIgnoreCase(nom);
        }

        if (produitCollection.size() == 0) {
            throw new GraphQLCustomException("Aucun produit(s) trouvé(s).");
        }
        return new ArrayList<>(ProduitTransformer.entityToDto(new ArrayList<>(produitCollection)));
    }

    /**
     * Permet la récuperation d'un produit par sa référence
     *
     * @param ref du produit recherché
     * @return le produit recherché
     */
    @Override
    public ProduitDTO getByRef(String ref) {

        Optional<Produit> produit = produitRepository.findById(ref);

        if (!produit.isPresent()) {
            throw new GraphQLCustomException("Aucun produit trouvé.");
        }

        ProduitDTO produitDTO = ProduitTransformer.entityToDto(produit.get());
        produitDTO.setNoteMoyenne(avisClientRepository.averageByReferenceProduit(ref));
        return produitDTO;
    }

    /**
     * Retourne une page de produit
     *
     * @param numeroPage    la page souhaitée
     * @param nombreProduit le nombre de produit à afficher dans la
     * @param nom           le nom du produit à rechercher
     * @param idCategorie   l'id de la catégorie recherchée
     * @return une page paginée
     */
    @Override
    public Page<Produit> getPage(int numeroPage, int nombreProduit, String nom, int idCategorie) {
        PageRequest page = (numeroPage == 0) ? PageRequest.of(numeroPage, nombreProduit) : PageRequest.of(numeroPage - 1, nombreProduit);
        if (nom != null && !nom.isEmpty() && idCategorie != 0) {
            Optional<Categorie> categorieOptional = categorieRepository.findById(idCategorie);
            if (!categorieOptional.isPresent()) {
                return Page.empty();
            }
            Categorie categorie = categorieOptional.get();
            return produitRepository.findByNomContainingIgnoreCaseAndCategories_borneGaucheGreaterThanEqualAndCategories_borneDroitLessThanEqual(page, nom, categorie.getBorneGauche(), categorie.getBorneDroit());
        } else if (idCategorie != 0) {
            Optional<Categorie> categorieOptional = categorieRepository.findById(idCategorie);
            if (!categorieOptional.isPresent()) {
                return Page.empty();
            }
            Categorie categorie = categorieOptional.get();
            return produitRepository.findByCategories(page, categorie);
        } else if (nom != null && !nom.isEmpty()) {
            return produitRepository.findByNomContainingIgnoreCase(page, nom);
        }
        return produitRepository.findAll(page);
    }

    @Override
    public Long countProduits() {
        return produitRepository.countProduits();
    }

    @Override
    public List<StatistiqueProduitCategorieDTO> countProduitsByCategorie() {
        Map<Categorie, Long> countProduitCategorie = produitRepository.countProduitsByCategories();
        List<StatistiqueProduitCategorieDTO> statistique = new ArrayList<>();
        countProduitCategorie.entrySet().stream().forEach(
                entry -> statistique.add(new StatistiqueProduitCategorieDTO(entry.getKey().getNomCategorie(), entry.getValue()))
        );
        return statistique;
    }
}
