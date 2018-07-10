package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieSupprimeBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.CategorieSupprime;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.CategorieSupprimeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service permettant de gérer les actions effectuées pour les catégories.
 */

@Service
@Transactional
public class CategorieSupprimeBusiness implements ICategorieSupprimeBusiness {
    /**
     * Constant permettant un décalage des bornes maximum
     */
    private static final int decalageBorne = +1000000;

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CategorieBusiness categorieBusiness;

    @Autowired
    private CategorieSupprimeRepository categorieSupprimeRepository;

    /**
     * @param categorieToSave la catégorie a supprimé
     * @return true si sauvegarde réussi sinon false.
     */
    @Override
    public boolean saveInCategorieSupprime(Categorie categorieToSave) {
        if (categorieToSave == null) {
            return false;
        }
        CategorieSupprime categorieSupprime = categorieToCategorieSupprime(categorieToSave);

        // on vérifie que la catégorie a bien été ajouté
        categorieSupprimeRepository.save(categorieSupprime);
        return true;
    }

    @NotNull
    private CategorieSupprime categorieToCategorieSupprime(Categorie categorieToSave) {
        CategorieSupprime categorieSupprime = new CategorieSupprime();
        categorieSupprime.setNomCategorie(categorieToSave.getNomCategorie());
        categorieSupprime.setLevel(categorieToSave.getLevel());
        categorieSupprime.setBorneDroit(categorieToSave.getBorneDroit());
        categorieSupprime.setBorneGauche(categorieToSave.getBorneGauche());
        if (categorieToSave.getProduits() != null) {
            // On realise une copie car categorie.setProduits(c.getProduits()) creer des conflits JPA (shared reference)
            List<Produit> produits = new ArrayList<Produit>(categorieToSave.getProduits());
            categorieSupprime.setProduits(produits);
        }
        return categorieSupprime;
    }

    /**
     * Methode effectuant les appels permettant de restaurer une categorie
     * notament le deplacement de la categorie supprime une fois insérer dans la table categorie
     *
     * @param nouveauParent id de la categorie parent de la categorie a restaurer
     * @return l'id de la categorie restauré ou 0 si la categorie n'a pas pu être restauré
     */
    @Override
    public List<CategorieDTO> restoreLastDeletedCategorie(int nouveauParent) {
        int idCatADeplacer = moveCategorieSupprime();
        if (categorieBusiness.moveCategorie(idCatADeplacer, nouveauParent)) {
            categorieSupprimeRepository.deleteAll();
            return categorieBusiness.getCategorie(idCatADeplacer, null, true, true);
        } else {
            return null;
        }
    }


    /**
     * Methode permettant de deplacer les bornes des categories supprimées afin de preparer l'insertion dans la table categorie
     * puis l'insére dans la table categorie
     *
     * @return l'id de la catégorie une fois insérer dans la table categorie
     */
    @Override
    public int moveCategorieSupprime() {
        // Aller chercher la catégorie à déplacer et ses enfants
        Iterable<CategorieSupprime> categoriesADeplacer = categorieSupprimeRepository.findAll();
        // On converties les catégorieSupprime en Categorie
        List<Categorie> categories = new ArrayList<>();
        for (CategorieSupprime catSupp : categoriesADeplacer) {
            Categorie categorie = new Categorie();
            categorie.setNomCategorie(catSupp.getNomCategorie());
            categorie.setLevel(catSupp.getLevel());
            if (catSupp.getProduits() != null) {
                // On realise une copie car categorie.setProduits(catSupp.getProduits()) creer des conflits JPA (shared reference)
                List<Produit> produits = new ArrayList<Produit>(catSupp.getProduits());
                categorie.setProduits(produits);
            }
            // On décale les bornes pour éviter les conflits lors de l'insertion dans la table catégorie
            categorie.setBorneDroit(catSupp.getBorneDroit() + decalageBorne);
            categorie.setBorneGauche(catSupp.getBorneGauche() + decalageBorne);
            categories.add(categorie);
        }
        Iterable<Categorie> results = categorieRepository.saveAll(categories);
        // -1 pour initialiser car aucune catégorie ne possède cette id
        int idCatADeplacer = -1;
        // Max_value afin d'être sur de récuperer le lvl d'une catégorie
        int lvlMin = Integer.MAX_VALUE;
        // On récupére l'id de la catégorie possèdant le lvl min, c'est à dire la catégorie à la plus haute hiérarchie
        for (Categorie c : results) {
            if (lvlMin > c.getLevel()) {
                lvlMin = c.getLevel();
                idCatADeplacer = c.getIdCategorie();
            }
        }
        return idCatADeplacer;
    }


}
