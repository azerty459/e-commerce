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
     * Méthode définissant l'ajout d'une catégorie.
     * @param nomCategorie Le nom de la catégorie
     * @return la catégorie crée
     */
    @Override
    public CategorieDTO addCategorie(String nomCategorie, String pere) {
        Optional<Categorie> categoriePereOptional = categorieRepository.findById(pere);
        if(categoriePereOptional.isPresent()){
            if(pere == null) {
                List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
                int borndeDroit = categorieList.get(0).getBorneDroit();
                for (int i = 0; i < categorieList.size(); i++) {
                    if (categorieList.get(i).getLevel() == 0 && categorieList.get(i).getBorneDroit() > borndeDroit) {
                        borndeDroit = categorieList.get(i).getBorneDroit();
                    }
                    // Insére la catégorie
                    Categorie categorieInserer = new Categorie();
                    categorieInserer.setNomCategorie(nomCategorie);
                    categorieInserer.setBorneGauche(borndeDroit+1);
                    categorieInserer.setBorneDroit(borndeDroit+2);
                    return CategorieTransformer.entityToDto(categorieRepository.save(categorieInserer), new ArrayList<>(categorieRepository.findAll()));
                }
            }else{
                // Checherche la catégorie père et ajoute + 2 à sa borne droite.
                Categorie categoriePere = categoriePereOptional.get();
                categoriePere.setBorneDroit(categoriePere.getBorneDroit() + 2);
                categorieRepository.save(categoriePere);
                //Permet de décaler les catégorie de + 2 par rapport à la borne droite du père
                List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
                for (int i = 0; i < categorieList.size(); i++) {
                    if (categorieList.get(i).getBorneDroit() > categoriePere.getBorneDroit()) {
                        Categorie retour = categorieList.get(i);
                        retour.setBorneGauche(retour.getBorneGauche() + 2);
                        retour.setBorneDroit(retour.getBorneDroit() + 2);
                        categorieRepository.save(retour);
                    }
                }
                // Insére la catégorie dans le père
                Categorie categorieInserer = new Categorie();
                categorieInserer.setNomCategorie(nomCategorie);
                categorieInserer.setBorneGauche(categoriePere.getBorneDroit());
                categorieInserer.setBorneDroit(categoriePere.getBorneDroit() + 1);
                return CategorieTransformer.entityToDto(categorieRepository.save(categorieInserer), new ArrayList<>(categorieRepository.findAll()));
            }
        }
        return null;
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
    public CategorieDTO getCategorieByNom(String nomCategorie) {
        Optional<Categorie> categorie = categorieRepository.findById(nomCategorie);
        return CategorieTransformer.entityToDto(categorie.orElse(null), new ArrayList<>(categorieRepository.findAll()));
    }
}
