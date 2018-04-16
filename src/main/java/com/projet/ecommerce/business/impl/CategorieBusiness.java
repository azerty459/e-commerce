package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.transformer.CategorieTransformer;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service permettant de gérer les actions effectuées pour les catégories.
 */

@Service
public class CategorieBusiness implements ICategorieBusiness {

    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Ajout d'une catégorie parent
     *
     * @param nomCategorie Le nom de la catégorie
     * @return la catégorie crée
     */
    @Override
    public CategorieDTO addParent(String nomCategorie) {
        //On récupère toute la liste des catégories
        List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());

        //On déclare une borne droit par défaut qui va être utilisée juste après
        int borndeDroit = categorieList.get(0).getBorneDroit();

        //On parcours la liste pour trouver la borne droit maximum dans la base de données par rapport au catégorie parent
        for (int i = 0; i < categorieList.size(); i++) {
            if (categorieList.get(i).getLevel() == 0 && categorieList.get(i).getBorneDroit() > borndeDroit) {
                borndeDroit = categorieList.get(i).getBorneDroit();
            }
        }

        // On insére la catégorie et on la return en CategorieDTO
        Categorie categorieInserer = new Categorie();
        categorieInserer.setNomCategorie(nomCategorie);
        categorieInserer.setBorneGauche(borndeDroit + 1);
        categorieInserer.setBorneDroit(borndeDroit + 2);
        categorieInserer.setLevel(1);
        categorieInserer.setProduits(new ArrayList<>());

        return CategorieTransformer.entityToDto(categorieRepository.save(categorieInserer), new ArrayList<>(categorieRepository.findAll()));
    }

    /**
     * Ajoute une catégorie enfant
     *
     * @param nomCategorie Le nom de la catégorie
     * @param parent       Le parent de la catégorie à insérer
     * @return la catégorie crée
     */
    @Override
    public CategorieDTO addEnfant(String nomCategorie, String parent) {
        // Ajout d'une categorie enfant
        Optional<Categorie> categorieParentOptional = categorieRepository.findById(parent);
        if (categorieParentOptional.isPresent()) {
            // Checherche la catégorie père et ajoute + 2 à sa borne droite.
            Categorie categorieParent = categorieParentOptional.get();

            //Permet de décaler les catégorie de + 2 par rapport à la borne droite du père
            List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
            for (int i = 0; i < categorieList.size(); i++) {
                if (categorieList.get(i).getBorneDroit() > categorieParent.getBorneDroit()) {
                    Categorie retour = categorieList.get(i);
                    retour.setBorneDroit(retour.getBorneDroit() + 2);
                    categorieRepository.save(retour);
                }
            }

            // On créer la catégorie à insérer
            Categorie categorieInserer = new Categorie();
            categorieInserer.setNomCategorie(nomCategorie);
            categorieInserer.setBorneGauche(categorieParent.getBorneDroit());
            categorieInserer.setBorneDroit(categorieParent.getBorneDroit() + 1);
            categorieInserer.setLevel(categorieParent.getLevel() + 1);
            categorieInserer.setProduits(new ArrayList<>());

            // On ajoute + 2 au père sur sa borne droite puis au sauvegarde
            categorieParent.setBorneDroit(categorieParent.getBorneDroit() + 2);
            categorieRepository.save(categorieParent);
            return CategorieTransformer.entityToDto(categorieRepository.save(categorieInserer), new ArrayList<>(categorieRepository.findAll()));
        }
        return null;
    }

    /**
     * Supprime la catégorie dans la base de données.
     *
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    @Override
    public boolean delete(String nomCategorie) {
        categorieRepository.deleteById(nomCategorie);
        return true;
    }

    /**
     * Retourne la liste complète des catégories liées à la base de données.
     *
     * @return une liste de catégorie
     */
    @Override
    public List<CategorieDTO> getAll() {
        return new ArrayList<>(CategorieTransformer.entityToDto(new ArrayList<>(categorieRepository.findAll())));
    }

    /**
     * Retourne une categorieDTO parent et ses éventuelle enfants.
     *
     * @param nomCategorie Le nom de la catégorie parent à rechercher.
     * @return une categorieDTO parent et ses éventuelle enfants, null si la catégorie parent n'est pas trouvé
     */
    @Override
    public CategorieDTO getByNom(String nomCategorie) {
        Optional<Categorie> categorie = categorieRepository.findById(nomCategorie);
        if (categorie.isPresent()) {
            return CategorieTransformer.entityToDto(categorie.get(), new ArrayList<>(categorieRepository.findAll()));
        }
        return null;
    }
}
