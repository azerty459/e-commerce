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
     * Ajout d'une catégorie
     * @param nomCategorie Le nom de la catégorie
     * @param pere Le pere de la catégorie à insérer
     * @return
     */
    @Override
    public CategorieDTO add(String nomCategorie, String pere) {
        if (pere.isEmpty() == true) {
            List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
            int borndeDroit = categorieList.get(0).getBorneDroit();
            for (int i = 0; i < categorieList.size(); i++) {
                if (categorieList.get(i).getLevel() == 0 && categorieList.get(i).getBorneDroit() > borndeDroit) {
                    borndeDroit = categorieList.get(i).getBorneDroit();
                }
                // Insére la catégorie
                Categorie categorieInserer = new Categorie();
                categorieInserer.setNomCategorie(nomCategorie);
                categorieInserer.setBorneGauche(borndeDroit + 1);
                categorieInserer.setBorneDroit(borndeDroit + 2);
                categorieInserer.setLevel(1);
                System.out.println(categorieInserer.getNomCategorie());
                return CategorieTransformer.entityToDto(categorieRepository.save(categorieInserer), new ArrayList<>(categorieRepository.findAll()));
            }
        }
        Optional<Categorie> categoriePereOptional = categorieRepository.findById(pere);
        if (categoriePereOptional.isPresent()) {
            // Checherche la catégorie père et ajoute + 2 à sa borne droite.
            Categorie categoriePere = categoriePereOptional.get();

            //Permet de décaler les catégorie de + 2 par rapport à la borne droite du père
            List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
            for (int i = 0; i < categorieList.size(); i++) {
                if (categorieList.get(i).getBorneDroit() > categoriePere.getBorneDroit()) {
                    Categorie retour = categorieList.get(i);
                    retour.setBorneDroit(retour.getBorneDroit() + 2);
                    categorieRepository.save(retour);
                }
            }

            // On créer la catégorie à insérer
            Categorie categorieInserer = new Categorie();
            categorieInserer.setNomCategorie(nomCategorie);
            categorieInserer.setBorneGauche(categoriePere.getBorneDroit());
            categorieInserer.setBorneDroit(categoriePere.getBorneDroit() + 1);
            categorieInserer.setLevel(categoriePere.getLevel()+1);

            // On ajoute + 2 au père sur sa borne droite puis au sauvegarde
            categoriePere.setBorneDroit(categoriePere.getBorneDroit() + 2);
            categorieRepository.save(categoriePere);
            return CategorieTransformer.entityToDto(categorieRepository.save(categorieInserer), new ArrayList<>(categorieRepository.findAll()));
        }
        return null;
    }

    /**
     * Supprime la catégorie dans la base de données.
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    @Override
    public boolean delete(String nomCategorie) {
        Optional<Categorie> categorie = categorieRepository.findById(nomCategorie);
        if (categorie.isPresent()) {
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
    public List<CategorieDTO> getAll() {
        return new ArrayList<>(CategorieTransformer.entityToDto(new ArrayList<>(categorieRepository.findAll())));
    }

    /**
     * Retourne la catégorie recherché
     * @param nomCategorie Le nom de la catégorie à rechercher
     * @return l'objet catégorie recherché sinon null, s'il n'est pas trouvé
     */
    @Override
    public CategorieDTO getByNom(String nomCategorie) {
        Optional<Categorie> categorie = categorieRepository.findById(nomCategorie);
        return CategorieTransformer.entityToDto(categorie.orElse(null), new ArrayList<>(categorieRepository.findAll()));
    }
}
