package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.entrypoint.graphQL.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.projet.ecommerce.utilitaire.utilitaire.mergeObjects;

/**
 * Service permettant de gérer les actions effectuées pour les produits.
 */

@Service
public class ProduitBusiness implements IProduitBusiness {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    @Qualifier("produitRepositoryCustomImpl")
    private ProduitRepositoryCustom produitRepositoryCustom;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private PhotoRepository photoRepository;

    /**
     * Ajoute un produit dans la base de données.
     *
     * @param referenceProduit La référence du produit
     * @param nom              Le nom du produit
     * @param description      Sa description
     * @param prixHT           Son prix hors taxe
     * @return l'objet produit crée ou null, s'il il manque une referenceProduit, un nom et un prixHT.
     */
    @Override
    public ProduitDTO add(String referenceProduit, String nom, String description, float prixHT, List<Integer> nouvelleCatList) {
        if (!referenceProduit.isEmpty() && !nom.isEmpty()) {
            if (produitRepository.findById(referenceProduit).isPresent()) {
                throw new GraphQLCustomException("Le produit à ajouter existe déjà.");
            }
            Produit produit = new Produit();
            produit.setReferenceProduit(referenceProduit);
            produit.setNom(nom);
            produit.setDescription(description);
            produit.setPrixHT(prixHT);
            produit.setCaracteristiques(new ArrayList<>());
            produit.setPhotos(new ArrayList<>());


            List<Categorie> categorieList = new ArrayList<>();
            if (nouvelleCatList != null) {
                for (int idCategorie : nouvelleCatList) {
                    Optional<Categorie> categorie = categorieRepository.findById(idCategorie);
                    if (categorie.isPresent()) {
                        categorieList.add(categorie.get());
                    }
                    //TODO else faire une erreur qui dit que la categorie n'existe pas mais n'arrete pas l'algorithme.
                }
            }
            produit.setCategories(categorieList);
            return ProduitTransformer.entityToDto(produitRepository.save(produit));
        } else {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout du produit (la référence, le nom et le prixHT ne peut être null)");
            graphQLCustomException.ajouterExtension("Référence", referenceProduit);
            graphQLCustomException.ajouterExtension("Nom", nom);
            graphQLCustomException.ajouterExtension("PrixHT", prixHT + "");
            throw graphQLCustomException;
        }
    }

    /**
     * Modifie le produit dans la base de données.
     *
     * @param produit L'objet produit à sauvegarder
     * @return l'objet produit modifié, une erreur si le produit n'a pas été trouvé
     */
    @Override
    public ProduitDTO update(Produit produit) {
        Optional<Produit> produitOptional = produitRepository.findById(produit.getReferenceProduit());
        if (produit.getNom().isEmpty()) {
            throw new GraphQLCustomException("Le nom du produit ne peut être vide.");
        }
        if (!produitOptional.isPresent()) {
            throw new GraphQLCustomException("Le produit recherché n'existe pas.");
        }

        // On remplace la liste de catégories par une liste contenant les vraies valeurs des catégories pour éviter la perte d'informations
        List<Categorie> categorieList = new ArrayList<>();
        for (Categorie categorie : produit.getCategories()) {
            Optional<Categorie> categorieOptional = categorieRepository.findById(categorie.getIdCategorie());
            if(categorieOptional.isPresent()){
                categorieList.add(categorieOptional.get());
            }else{
                throw new GraphQLCustomException("La catégorie n'existe pas.");
            }
        }
        produit.setCategories(categorieList);

        // On remplace la liste de photos par une liste contenant les vraies valeurs des photos pour éviter la perte d'informations
        List<Photo> photoList = new ArrayList<>();
        for (Photo photo : produit.getPhotos()) {
            Optional<Photo> photoOptional = photoRepository.findById(photo.getIdPhoto());
            if(photoOptional.isPresent()){
                photoList.add(photoOptional.get());
            }else{
                throw new GraphQLCustomException("La catégorie n'existe pas.");
            }
        }
        produit.setPhotos(photoList);

        // On fusionne les deux produits en un
        Produit retourProduit = produitOptional.get();
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
     * @return une liste de produits selon les paramètres ci-dessous
     */
    @Override
    public List<ProduitDTO> getAll(String ref, String nom, String cat) {

        Collection<Produit> produitCollection;

        if(nom == null) {
            produitCollection = produitRepositoryCustom.findAllWithCriteria(ref, cat);

        } else {
            produitCollection = produitRepository.findByNomContainingIgnoreCase(nom);
        }

        if(produitCollection.size() == 0){
            throw new GraphQLCustomException("Aucun produit(s) trouvé(s).");
        }

        return new ArrayList<>(ProduitTransformer.entityToDto(new ArrayList<>(produitCollection)));
    }


    /**
     * Retourne le produit recherché.
     *
     * @param referenceProduit Référence du produit à rechercher
     * @return l'objet produit recherché sinon une exception, s'il n'est pas trouvé
     */
    @Override
    public ProduitDTO getByRef(String referenceProduit) {
        Optional<Produit> produit = produitRepository.findById(referenceProduit);
        if (produit.isPresent()) {
            return ProduitTransformer.entityToDto(produit.get());
        } else {
            throw new GraphQLCustomException("Le produit recherche n'existe pas.");
        }
    }

    /**
     * Retourne un objet page de produit
     *
     * @param pageNumber la page souhaitée
     * @param nb         le nombre de produit à afficher dans la page
     * @return un objet page de produit
     */
    @Override
    public Page<Produit> getPage(int pageNumber, int nb, String nom) {

        PageRequest page = (pageNumber == 0)? PageRequest.of(pageNumber, nb): PageRequest.of(pageNumber-1, nb);

        if(nom == null) {
            return produitRepository.findAll(page);
        } else {
            // On recherche un produit selon son nom
            return produitRepository.findByNomContainingIgnoreCase(page, nom);
        }

    }
}
