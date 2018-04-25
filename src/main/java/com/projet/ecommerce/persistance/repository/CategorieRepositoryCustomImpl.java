package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class CategorieRepositoryCustomImpl implements CategorieRepositoryCustom {

    // Requêtes JPQL
    private static final String SQL_ALL_CATEGORIES = "SELECT c FROM Categorie AS c";

    private static final String SQL_CATEGORY_BY_NAME = "SELECT c FROM Categorie AS c WHERE c.nomCategorie =:nom";

    @Autowired
    private EntityManager entityManager;

    /**
     * Méthode allant chercher les catégories (toutes ou par nom)
     * @param nom le nom de la catégorie recherchée
     * @return une collection de la / des catégorie(s) trouvée(s)
     */
    public Collection<Categorie> findAll(String nom) {

        String sql = "";
        Query query = null;

        if(nom == null) {
            query =  entityManager.createQuery(SQL_ALL_CATEGORIES, Categorie.class);

        }
        else {
            query =  entityManager.createQuery(SQL_CATEGORY_BY_NAME, Categorie.class);
            query.setParameter("nom", nom);

        }

        return query.getResultList();
    }
}
