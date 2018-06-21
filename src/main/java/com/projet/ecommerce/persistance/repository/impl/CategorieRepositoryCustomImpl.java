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
    public void ecarterBornes(Categorie cat, int decalage) {

        System.out.println("ECARTER BORNES");
        System.out.println("Id catégorie:");
        System.out.println(cat.getIdCategorie());
        System.out.println("Décalage:");
        System.out.println(decalage);

        Query query1;
        Query query2;

        query1 = entityManager.createQuery(SQL_CATEGORIES_ECARTER_BORNES_GAUCHES);
        query1.setParameter("i", decalage);
        query1.setParameter("limite", cat.getBorneGauche());
        query1.executeUpdate();

        query2 = entityManager.createQuery(SQL_CATEGORIES_ECARTER_BORNES_DROITES);
        query2.setParameter("i", decalage);
        query2.setParameter("limite", cat.getBorneGauche());
        query2.executeUpdate();

    }


    @Override
    public int rearrangerBornes(int bg, int bd, int intervalle) {

        System.out.println("REARRANGER BORNES");
        System.out.println("bg: ");
        System.out.println(bg);
        System.out.println("bd: ");
        System.out.println(bd);
        System.out.println("Intervalle: ");
        System.out.println(intervalle);

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

    // NOUVELLE METHODES

    private static final String SQL_DEPLACE_BORNE_GAUCHE = "UPDATE Categorie AS c " +
            "SET c.borneGauche = c.borneGauche + :depl WHERE c.idCategorie = :id";

    private static final String SQL_DEPLACE_BORNE_DROITE = "UPDATE Categorie AS c " +
            "SET c.borneDroit = c.borneDroit + :depl WHERE c.idCategorie = :id";

    @Override
    public void changerBornes(int idCategorie, int deplacement) {

        System.out.println("CHANGER BORNES");
        System.out.println("id: ");
        System.out.println(idCategorie);
        System.out.println("Déplacement: ");
        System.out.println(deplacement);

        Query query1 = entityManager.createQuery(SQL_DEPLACE_BORNE_GAUCHE);
        query1.setParameter("depl", deplacement);
        query1.setParameter("id", idCategorie);
        query1.executeUpdate();

        Query query2 = entityManager.createQuery(SQL_DEPLACE_BORNE_DROITE);
        query2.setParameter("depl", deplacement);
        query2.setParameter("id", idCategorie);
        query2.executeUpdate();

    }

    private static final String SQL_CHANGE_LEVEL = "UPDATE Categorie AS c" +
            " SET c.level = :nl WHERE c.idCategorie = :id";

    @Override
    public void changerLevel(int idCategorie, int nouveauLevel) {

        System.out.println("CHANGER LEVEL");
        System.out.println("id: ");
        System.out.println(idCategorie);
        System.out.println("Nouveau Level: ");
        System.out.println(nouveauLevel);

        Query query = entityManager.createQuery(SQL_CHANGE_LEVEL);
        query.setParameter("nl", nouveauLevel);
        query.setParameter("id", idCategorie);
        query.executeUpdate();

    }


}
