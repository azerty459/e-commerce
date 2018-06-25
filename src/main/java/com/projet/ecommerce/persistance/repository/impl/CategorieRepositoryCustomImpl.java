package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class CategorieRepositoryCustomImpl implements CategorieRepositoryCustom {

    /* ***** Requêtes JPQL ***** */

    // Aller chercher une catégorie parente directe d'une catégorie
    private static final String SQL_PARENT_DIRECT = "SELECT c FROM Categorie AS c WHERE c.level =:l AND c.borneGauche < :bg AND c.borneDroit > :bd";

    // Ecarter d'un intervalle i les bornes gauches ou droites supérieures à un nombre limite
    private static final String SQL_CATEGORIES_ECARTER_BORNES_GAUCHES = "UPDATE Categorie as c " +
            "SET c.borneGauche = c.borneGauche + :i WHERE c.borneGauche > :limite";
    private static final String SQL_CATEGORIES_ECARTER_BORNES_DROITES = "UPDATE Categorie as c " +
            "SET c.borneDroit = c.borneDroit + :i WHERE c.borneDroit > :limite";

    // Réarranger les bornes suite à la suppression d'une catégorie
    private static final String SQL_DECALER_BORNES_GAUCHES = "UPDATE Categorie AS c " +
            "SET c.borneGauche = c.borneGauche - :i WHERE c.borneGauche > :bg";
    private static final String SQL_DECALER_BORNES_DROITES = "UPDATE Categorie AS c " +
            "SET c.borneDroit = c.borneDroit - :i WHERE c.borneDroit > :bg";

    // Chercher la borne maximale dans toute la base de données
    private static final String SQL_BORNE_MAX = "SELECT MAX(borneDroit) FROM Categorie";

    // Déplacer les catégories (changer leurs bornes et leur level)
    private static final String SQL_CHANGER_BORNES_ET_LEVEL = "UPDATE Categorie AS c " +
            "SET c.borneGauche = c.borneGauche + :depl, c.borneDroit = c.borneDroit + :depl, c.level = c.level + :nl " +
            "WHERE c.idCategorie IN :ids";

    @Autowired
    private EntityManager entityManager;

    /**
     * Récupérer les catégories parents de la catégorie de nom donné en paramètre
     *
     * @param cats les catégories dont on doit rechercher les parents
     * @return une collection des catégories parents de cette catégorie
     */
    @Override
    public Collection<Categorie> findParents(Map<Integer, Categorie> cats) {

        if (!cats.isEmpty()) {
            // Construire la requête
            String sql = "SELECT p FROM Categorie AS p WHERE ";

            // // Itérer sur le HashMap de catégories à rechercher
            int taille = cats.size();
            // TODO StringBuilder
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < taille; i++) {
                sb.append("toto").append(cats);
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
            sb.toString();
            // Lancer la requête
            TypedQuery<Categorie> query = entityManager.createQuery(sql, Categorie.class);

            return query.getResultList();
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * Renvoie la catégorie directement parent d'une catégorie donnée en paramètre.
     *
     * @param cat la catégorie dont on doit chercher le parent.
     * @return La catégorie parent.
     */
    @Override
    public Categorie findDirectParent(Categorie cat) {

        Categorie resultat;

        // Cas où la catégorie est de niveau au moins 2
        if (cat.getLevel() > 1) {


            // On crée la requête pour aller chercher le parent direct de cat
            TypedQuery<Categorie> query = entityManager.createQuery(SQL_PARENT_DIRECT, Categorie.class);

            query.setParameter("l", cat.getLevel() - 1);
            query.setParameter("bg", cat.getBorneGauche());
            query.setParameter("bd", cat.getBorneDroit());

            // On retourne la catégorie unique, parent de cat
            resultat = query.getSingleResult();
        }
        // Pas de parent pour une catégorie de niveau 1
        else {
            resultat = new Categorie();
            resultat.setNomCategorie("Aucune catégorie parente");
        }

        return resultat;

    }

    @Override
    public void ecarterBornes(int bg, int decalage) {

        Query query1;
        Query query2;

        query1 = entityManager.createQuery(SQL_CATEGORIES_ECARTER_BORNES_GAUCHES);
        query1.setParameter("i", decalage);
        query1.setParameter("limite", bg);
        query1.executeUpdate();

        query2 = entityManager.createQuery(SQL_CATEGORIES_ECARTER_BORNES_DROITES);
        query2.setParameter("i", decalage);
        query2.setParameter("limite", bg);
        query2.executeUpdate();

    }

    @Override
    public int rearrangerBornes(int bg, int intervalle) {

        System.out.println("Réarrangement des bornes");

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

    @Override
    public int findBorneMax() {

        Query query;
        query = entityManager.createQuery(SQL_BORNE_MAX);

        Integer a = (Integer) query.getSingleResult();
        System.out.println(a);

        return a;
    }


    @Override
    public void changerBornesEtLevel(List<Integer> ids, int intervalleDeDeplacement, int intervalLevel) {
        Query query = entityManager.createQuery(SQL_CHANGER_BORNES_ET_LEVEL);
        query.setParameter("nl", intervalLevel);
        query.setParameter("ids", ids);
        query.setParameter("depl", intervalleDeDeplacement);
        query.executeUpdate();
    }


}
