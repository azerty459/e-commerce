package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.entrypoint.graphQL.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public ProduitDTO add(String referenceProduit, String nom, String description, Double prixHT, List<String> nouvelleCatList) {
        if (!referenceProduit.isEmpty() && !nom.isEmpty()) {
            if (produitRepository.findById(referenceProduit).isPresent()){
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
            if(nouvelleCatList != null) {
                for(String Nomcategorie: nouvelleCatList){
                    Optional<Categorie> categorie = categorieRepository.findCategorieByNomCategorie(Nomcategorie);
                    if (categorie.isPresent()) {
                        categorieList.add(categorie.get());
                    }
                    //TODO else faire une erreur qui dit que la categorie n'existe pas mais n'arrete pas l'algorithme.
                }
            }
            produit.setCategories(categorieList);
            return ProduitTransformer.entityToDto(produitRepository.save(produit));
        }else{
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout du produit (la référence du produit et le nom ne peut être null)");
            graphQLCustomException.ajouterExtension("Référence", referenceProduit);
            graphQLCustomException.ajouterExtension("Nom", nom);
            throw graphQLCustomException;
        }
    }

    /**
     * Modifie le produit dans la base de données.
     *
     * @param referenceProduit La référence du produit à modifier
     * @param nom              Le nouveau nom
     * @param description      La nouvelle description
     * @param prixHT           Le nouveau prix hors taxe
     * @return l'objet produit modifié, null si le produit à modifier n'est pas trouvée
     */
    @Override
    public ProduitDTO update(String referenceProduit, String nom, String description, Double prixHT, String nouvelleCat, String supprimerCat) {
        Optional<Produit> produitOptional = produitRepository.findById(referenceProduit);
        if (produitOptional.isPresent()) {
            Produit produit = produitOptional.get();

            if(nom != null)
                produit.setNom(nom);

            if(description != null)
                produit.setDescription(description);

            if(prixHT != null)
                produit.setPrixHT(prixHT);

            List<Categorie> categorieList = produit.getCategories();

            if (nouvelleCat != null){
                Optional<Categorie> categorieAjout = categorieRepository.findCategorieByNomCategorie(nouvelleCat);
                if(categorieAjout.isPresent()){
                    categorieList.add(categorieAjout.get());
                }else{
                    throw new GraphQLCustomException("La catégorie associer au produit n'existe pas.");
                }
            }

            if(supprimerCat != null){
                Optional<Categorie> categorieSupprimer = categorieRepository.findCategorieByNomCategorie(supprimerCat);
                if(categorieSupprimer.isPresent()){
                    categorieList.remove(categorieSupprimer.get());
                }else{
                    throw new GraphQLCustomException("La catégorie associer au produit n'existe pas.");
                }
            }

            produit.setCategories(categorieList);
            return ProduitTransformer.entityToDto(produitRepository.save(produit));
        }else{
            throw new GraphQLCustomException("Le produit recherché n'existe pas.");
        }
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
     * @param ref la référence du produit recherché
     * @param cat la catégorie du /des produit(s) recherché(s)
     * @return une liste de produits selon les paramètres ci-dessous
     */
    @Override
    public List<ProduitDTO> getAll(String ref, String cat) {
        Collection<Produit> produitCollection = produitRepositoryCustom.findAllWithCriteria(ref, cat);
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
        if (produit.isPresent()){
            return ProduitTransformer.entityToDto(produit.get());
        }else{
            throw new GraphQLCustomException("Le produit recherche n'existe pas.");
        }
    }

    /**
     * Retourne une liste de produit en fonction de la catégorie recherché.
     *
     * @param nomCategorie Le nom de catégorie
     * @return une liste d'objet produit
     */
    @Override
    public List<ProduitDTO> getByCategorie(String nomCategorie) {
        Optional<Categorie> optionalCategorie = categorieRepository.findCategorieByNomCategorie(nomCategorie);
        if (optionalCategorie.isPresent()) {
            Categorie categorie = optionalCategorie.get();
            List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
            List<Produit> produitList = new ArrayList<>();
            for (Categorie retourCategorie : categorieList) {
                if (categorie.getBorneGauche() < retourCategorie.getBorneGauche() && categorie.getBorneDroit() > retourCategorie.getBorneDroit()) {
                    produitList.addAll(retourCategorie.getProduits());
                }
            }
            return new ArrayList<>(ProduitTransformer.entityToDto(produitList));
        }
        return null;
    }

    /**
     * Retourne un objet page de produit
     * @param pageNumber la page souhaitée
     * @param nb le nombre de produit à afficher dans la page
     * @return un objet page de produit
     */
    @Override
    public Page<Produit> getPage(int pageNumber, int nb) {
        return  produitRepository.findAll(PageRequest.of(pageNumber - 1, nb));
    }
}
