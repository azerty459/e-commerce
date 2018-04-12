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
    public CategorieDTO addCategorie(CategorieDTO categorieDTO) {
        categorieRepository.save(CategorieTransformer.dtoToEntity(categorieDTO));
        return categorieDTO;
    }

    /**
     * Supprime la catégorie dans la base de données.
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    @Override
    public boolean deleteCategorie(String nomCategorie) {
        Optional<Categorie> categorie = categorieRepository.findById(nomCategorie);
        if(categorie.isPresent()){
            categorieRepository.delete(categorie.get());
            return true;
        }
        return false;
    }

    /**
     * Retourne la liste complète des catégories liées à la base de données.
     * @return une liste de catégorie
     */
    @Override
    public List<CategorieDTO> getCategorie() {
        return CategorieTransformer.entityToDto(new ArrayList<>(categorieRepository.findAll()));
    }

    /**
     * Retourne la catégorie recherché
     * @param nomCategorie Le nom de la catégorie à rechercher
     * @return l'objet catégorie recherché sinon null, s'il n'est pas trouvé
     */
    @Override
    public CategorieDTO getCategorieByID(String nomCategorie) {
        Optional<Categorie> categorie = categorieRepository.findById(nomCategorie);
        return CategorieTransformer.entityToDto(categorie.orElse(null), new ArrayList<>(categorieRepository.findAll()));
    }
}
