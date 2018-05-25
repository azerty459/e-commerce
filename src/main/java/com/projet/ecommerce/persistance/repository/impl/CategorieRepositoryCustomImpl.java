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

    /* ***** Requêtes JPQL ***** */

    // Requête pour aller chercher toutes les catégories
    private static final String SQL_ALL_CATEGORIES = "SELECT c FROM Categorie AS c ORDER BY c.borneGauche ASC";

    // Récupérer une catégorie grâce à son nom
    private static final String SQL_CATEGORY_BY_NAME = "SELECT c FROM Categorie AS c WHERE c.nomCategorie =:nom";

    // Aller chercher une catégorie grâce à son nom + ses sous-catégories
    private static final String SQL_CATEGORY_BY_NAME_SOUSCAT = "SELECT souscat FROM Categorie AS souscat WHERE souscat.borneGauche >= " +
            "(SELECT maincat.borneGauche FROM Categorie AS maincat WHERE maincat.nomCategorie =:nom) " +
            "AND souscat.borneDroit <= " +
            "(SELECT maincat2.borneDroit FROM Categorie AS maincat2 WHERE maincat2.nomCategorie =:nom)";

    // Aller chercher une seule catégorie d'id donnée en paramètre.
    private static final String SQL_CATEGORY_BY_ID = "SELECT c FROM Categorie AS c WHERE c.idCategorie = :id";

    // Aller chercher une catégorie par son id + ses sous-catégories
    private static final String SQL_CATEGORY_BY_ID_SOUSCAT = "SELECT souscat FROM Categorie AS souscat WHERE souscat.borneGauche >= " +
            "(SELECT maincat.borneGauche FROM Categorie AS maincat WHERE maincat.idCategorie =:id) " +
            "AND souscat.borneDroit <= " +
            "(SELECT maincat2.borneDroit FROM Categorie AS maincat2 WHERE maincat2.idCategorie =:id)";


    // Aller chercher une catégorie parente directe d'une catégorie
    private static final String SQL_PARENT_DIRECT = "SELECT c FROM Categorie AS c WHERE c.level =:l AND c.borneGauche < :bg AND c.borneDroit > :bd";

    // Aller chercher les catégories de borne gauche supérieure
    private static final String SQL_CATEGORIES_BORNE_GAUCHE_SUP = "SELECT c FROM Categorie AS c WHERE c.borneGauche > :bg";

    // Réarranger les bornes suite à la suppression d'une catégorie
    private static final String  SQL_DECALER_BORNES_GAUCHES = "UPDATE Categorie AS c " +
            "SET c.borneGauche = c.borneGauche - :i WHERE c.borneGauche > :bg";
    private static final String SQL_DECALER_BORNES_DROITES = "UPDATE Categorie AS c " +
            "SET c.borneDroit = c.borneDroit - :i WHERE c.borneDroit > :bg";

    @Autowired
    private EntityManager entityManager;

    /**
     * Méthode allant chercher les catégories (toutes ou par nom)
     * @param nom le nom de la catégorie recherchée
     * @return une collection de la / des catégorie(s) trouvée(s)
     */
    @Override
    public Collection<Categorie> findAllWithCriteria(int id, String nom, Boolean sousCat) {

        Query query = null;

        // Si on ne demande pas les sous-catégories: son recherche soit par id, soit par nom.
        // Si id = 0: on recherche la catégorie unique d'id donnée
        // Si id = 0 et nom == null, on retourne toutes les catégories
        if(!sousCat) {
            if(id != 0) {
                query = entityManager.createQuery(SQL_CATEGORY_BY_ID, Categorie.class);
                query.setParameter("id", id);
            } else if(nom == null) {
                query =  entityManager.createQuery(SQL_ALL_CATEGORIES, Categorie.class);
            } else if(nom != null) {
                query =  entityManager.createQuery(SQL_CATEGORY_BY_NAME, Categorie.class);
                query.setParameter("nom", nom);
            }
        } else {
            // On demande de retourner les sous-catégories
            // Si id != 0, on retourne une seule catégorie d'id donnée et ses sous-catégories
            // Si id = 0 et nom != null, on retourne la catégorie de nom donné et ses sous-catégories
            if(id != 0) {
                query = entityManager.createQuery(SQL_CATEGORY_BY_ID_SOUSCAT, Categorie.class);
                query.setParameter("id", id);
            } else if(nom != null) {
                query =  entityManager.createQuery(SQL_CATEGORY_BY_NAME_SOUSCAT, Categorie.class);
                query.setParameter("nom", nom);
            }
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

        Categorie resultat;

        // Cas où la catégorie est de niveau au moins 2
        if(cat.getLevel() > 1) {

            Query query;

            // On crée la requête pour aller chercher le parent direct de cat
            query = entityManager.createQuery(SQL_PARENT_DIRECT, Categorie.class);

            query.setParameter("l", cat.getLevel() - 1);
            query.setParameter("bg", cat.getBorneGauche());
            query.setParameter("bd", cat.getBorneDroit());

            // On retourne la catégorie unique, parent de cat

            resultat = (Categorie) query.getSingleResult();

        }

        // Pas de parent pour une catégorie de niveau 1
        else {

            resultat = new Categorie();
            resultat.setNomCategorie("Aucune catégorie parente");
        }

        return resultat;

    }

    @Override
    public Collection<Categorie> findAllCategoriesAvecBorneGaucheSuperieure(Categorie cat) {

        Collection<Categorie> parents = null;

        Query query = entityManager.createQuery(SQL_CATEGORIES_BORNE_GAUCHE_SUP, Categorie.class);
        query.setParameter("bg", cat.getBorneGauche());
        parents = query.getResultList();

        return parents;
    }

    /**
     *
     * @param bg borne gauche de la catégorie supprimée
     * @param bd borne droite de la catégorie supprimée
     * @param intervalle intervalle entre les 2
     * @return ne nombre de catégories réorganisées
     */
    @Override
    public int rearrangerBornes(int bg, int bd, int intervalle) {

        Query query1;
        Query query2;

        query1 = entityManager.createQuery(SQL_DECALER_BORNES_GAUCHES);
        query1.setParameter("i", intervalle);
        query1.setParameter("bg", bg);

        int nb1 = query1.executeUpdate();

        query2 = entityManager.createQuery(SQL_DECALER_BORNES_DROITES);
        query2.setParameter("i", intervalle);
        query2.setParameter("bg", bg);

        int nb2 = query2.executeUpdate();

        return Math.max(nb1, nb2);
    }

    // US#193 - FIN
}
