package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieSupprimeBusiness;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.CategorieSupprime;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.CategorieSupprimeRepository;
import com.projet.ecommerce.persistance.repository.impl.CategorieRepositoryCustomImpl;
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
    private final int decalageBorne = -999999;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CategorieBusiness categorieBusiness;

    @Autowired
    private CategorieSupprimeRepository categorieSupprimeRepository;

    @Autowired
    private CategorieRepositoryCustomImpl categorieRepositoryCustom;

    /**
     * @param categorieToSave la catégorie a supprimé
     * @return true si sauvegarde réussi sinon false.
     */
    @Override
    public boolean saveInCategorieSupprime(Categorie categorieToSave) {
        if (categorieToSave.getNomCategorie() != null) {
            CategorieSupprime categorieSupprime = new CategorieSupprime();
            categorieSupprime.setIdCategorie(categorieToSave.getIdCategorie());
            categorieSupprime.setNomCategorie(categorieToSave.getNomCategorie());
            categorieSupprime.setLevel(categorieToSave.getLevel());
            categorieSupprime.setBorneDroit(categorieToSave.getBorneDroit());
            categorieSupprime.setBorneGauche(categorieToSave.getBorneGauche());
            if (categorieToSave.getProduits() != null) {
                // On realise une copie car categorie.setProduits(c.getProduits()) creer des conflits JPA (shared reference)
                List<Produit> produits = new ArrayList<Produit>(categorieToSave.getProduits());
                categorieSupprime.setProduits(produits);
            }
            // on vérifie que la catégorie a bien été ajouté
            return categorieSupprimeRepository.save(categorieSupprime).getNomCategorie().equals(categorieToSave.getNomCategorie());
        } else {
            return false;
        }

    }

    /**
     * Methode effectuant les appels permettant de restaurer une categorie
     * notament le deplacement de la categorie supprime une fois insérer dans la table categorie
     *
     * @param nouveauParent id de la categorie parent de la categorie a restaurer
     * @return true si restauration réussite sinon false
     */
    @Override
    public int restoreLastDeletedCategorie(int nouveauParent) {
        int idCatADeplacer = moveCategorieSupprime();
        if (categorieBusiness.moveCategorie(idCatADeplacer, nouveauParent)) {
            categorieSupprimeRepository.deleteAll();
            return idCatADeplacer;
        } else {
            return 0;
        }
    }


    /**
     * Methode permettant de deplacer les bornes des categories supprimés afin preparer l'insertion dans la table categorie
     * puis l'insére dans la table categorie
     *
     * @return l'id de la catégorie une fois insérer dans la table categorie
     */
    @Override
    public int moveCategorieSupprime() {
        // Aller chercher la catégorie à déplacer et ses enfants
        List<CategorieSupprime> categoriesADeplacer = new ArrayList<>(categorieSupprimeRepository.findAll());
        // On converties les catégorieSupprime en Categorie
        List<Categorie> categories = new ArrayList<>();
        for (CategorieSupprime c : categoriesADeplacer) {
            Categorie categorie = new Categorie();
            categorie.setNomCategorie(c.getNomCategorie());
            categorie.setLevel(c.getLevel());
            if (categorie.getProduits() != null) {
                // On realise une copie car categorie.setProduits(c.getProduits()) creer des conflits JPA (shared reference)
                List<Produit> produits = new ArrayList<Produit>(c.getProduits());
                categorie.setProduits(produits);
            }
            // On décale les bornes pour éviter les conflits lors de l'insertion dans la table catégorie
            categorie.setBorneDroit(c.getBorneDroit() + decalageBorne);
            categorie.setBorneGauche(c.getBorneGauche() + decalageBorne);
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
