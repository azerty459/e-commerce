package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.entity.Categorie;
import com.projet.ecommerce.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieBusiness implements ICategorieBusiness {

    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Ajoute une catégorie dans la base de données.
     * @param categorie Un objet de type Categorie
     * @return l'objet catégorie créé
     */
    @Override
    public Categorie addCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    /**
     * Supprime la catégorie dans la base de données.
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    @Override
    public boolean deleteCategorie(String nomCategorie) {
        categorieRepository.deleteById(nomCategorie);
        return true;
    }

    /**
     * Retourne la liste complète des catégories liées à la base de données.
     * @return une liste de catégorie
     */
    @Override
    public List<Categorie> getCategorie() {
        return new ArrayList<>(categorieRepository.findAll());
    }

    /**
     * Retourne la catégorie recherché
     * @param nomCategorie Le nom de la catégorie à rechercher
     * @return l'objet catégorie recherché sinon null, s'il n'est pas trouvé
     */
    @Override
    public Categorie getCategorieByID(String nomCategorie) {
        Optional<Categorie> categorie = categorieRepository.findById(nomCategorie);
        if(categorie.isPresent()){
            return categorie.get();
        }
        return null;
    }
}
