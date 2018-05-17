package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.HashMap;

@Repository
public class CategorieRepositoryCustomImpl implements CategorieRepositoryCustom {

    // Requêtes JPQL
    private static final String SQL_ALL_CATEGORIES = "SELECT c FROM Categorie AS c ORDER BY c.borneGauche ASC";

    private static final String SQL_CATEGORY_BY_NAME = "SELECT souscat FROM Categorie AS souscat WHERE souscat.borneGauche >= " +
            "(SELECT maincat.borneGauche FROM Categorie AS maincat WHERE maincat.nomCategorie =:nom) " +
            "AND souscat.borneDroit <= " +
            "(SELECT maincat2.borneDroit FROM Categorie AS maincat2 WHERE maincat2.nomCategorie =:nom)";

    private static final String SQL_PARENT_DIRECT = "SELECT c FROM Categorie AS c WHERE c.level =:l AND c.borneGauche < :bg AND c.borneDroit > :bd";

    @Autowired
    private EntityManager entityManager;

    /**
     * Méthode allant chercher les catégories (toutes ou par nom)
     * @param nom le nom de la catégorie recherchée
     * @return une collection de la / des catégorie(s) trouvée(s)
     */
    @Override
    public Collection<Categorie> findAllWithCriteria(String nom) {

        Query query;

        if(nom == null) {
            query =  entityManager.createQuery(SQL_ALL_CATEGORIES, Categorie.class);

        }
        else {
            query =  entityManager.createQuery(SQL_CATEGORY_BY_NAME, Categorie.class);
            query.setParameter("nom", nom);
        }

        return query.getResultList();

    }

    // US#192 - DEBUT
    /**
     * Récupérer les catégories parents de la catégorie de nom donné en paramètre
     * @param cats les catégories dont on doit rechercher les parents
     * @return une collection des catégories parents de cette catégorie
     */
    @Override
    public Collection<Categorie> findParents(HashMap<Integer,Categorie> cats) {

        Query query;

        // Construire la requête
        String sql = "SELECT p FROM Categorie AS p WHERE ";

        // // Itérer sur le HashMap de catégories à rechercher
        int taille = cats.size();
        for(int i = 1; i < taille; i++) {
            sql += "(p.borneGauche >= ";
            sql += cats.get(i).getBorneGauche();
            sql += " AND p.borneDroit <= ";
            sql += cats.get(i).getBorneDroit();
            sql += ") OR ";
        }
        sql += "(p.borneGauche >= ";
        sql += cats.get(taille).getBorneGauche();
        sql += " AND p.borneDroit <= ";
        sql += cats.get(taille).getBorneDroit();
        sql += ")";

        // Lancer la requête
        query = entityManager.createQuery(sql, Categorie.class);

        return query.getResultList();

    }
    // US#192 - FIN

    // US#193 - DEBUT

    /**
     * Renvoie la catégorie directement parent d'une catégorie donnée en paramètre.
     * @param cat la catégorie dont on doit chercher le parent.
     * @return La catégorie parent.
     */
    @Override
    public Categorie findDirectParent(Categorie cat) {

        // TODO: gérer le cas où on est déjà au level 1 ? (à priori il ne retourne rien car pas de categorie de niveau 0)

        Query query;

        // On crée la requête pour aller chercher le parent direct de cat
        query = entityManager.createQuery(SQL_PARENT_DIRECT, Categorie.class);

        query.setParameter("l", cat.getLevel() - 1);
        query.setParameter("bg", cat.getBorneGauche());
        query.setParameter("bd", cat.getBorneDroit());

        // On retourne la catégorie unique, parent de cat
        return (Categorie) query.getSingleResult();

    }

    // US#193 - FIN
}
