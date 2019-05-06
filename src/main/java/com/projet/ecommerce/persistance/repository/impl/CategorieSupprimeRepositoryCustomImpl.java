package com.projet.ecommerce.persistance.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.CategorieSupprime;
import com.projet.ecommerce.persistance.repository.CategorieSupprimeRepositoryCustom;

@Repository
public class CategorieSupprimeRepositoryCustomImpl implements CategorieSupprimeRepositoryCustom {
    /* ***** Requêtes JPQL ***** */

    // Aller chercher une catégorie parente directe d'une catégorie
    private static final String SQL_PARENT_DIRECT = "SELECT c FROM CategorieSupprime AS c WHERE c.level =:l AND c.borneGauche < :bg AND c.borneDroit > :bd";

    // Ecarter d'un intervalle i les bornes gauches ou droites supérieures à un nombre limite
    private static final String SQL_CATEGORIES_ECARTER_BORNES_GAUCHES = "UPDATE CategorieSupprime as c " +
            "SET c.borneGauche = c.borneGauche + :i WHERE c.borneGauche > :limite";
    private static final String SQL_CATEGORIES_ECARTER_BORNES_DROITES = "UPDATE CategorieSupprime as c " +
            "SET c.borneDroit = c.borneDroit + :i WHERE c.borneDroit > :limite";

    // Réarranger les bornes suite à la suppression d'une catégorie
    private static final String SQL_DECALER_BORNES_GAUCHES = "UPDATE CategorieSupprime AS c " +
            "SET c.borneGauche = c.borneGauche - :i WHERE c.borneGauche > :bg";
    private static final String SQL_DECALER_BORNES_DROITES = "UPDATE CategorieSupprime AS c " +
            "SET c.borneDroit = c.borneDroit - :i WHERE c.borneDroit > :bg";

    // Chercher la borne maximale dans toute la base de données
    private static final String SQL_BORNE_MAX = "SELECT MAX(borneDroit) FROM CategorieSupprime";

    // Déplacer les catégories (changer leurs bornes et leur level)
    private static final String SQL_CHANGER_BORNES_ET_LEVEL = "UPDATE CategorieSupprime AS c " +
            "SET c.borneGauche = c.borneGauche + :depl, c.borneDroit = c.borneDroit + :depl, c.level = c.level + :nl " +
            "WHERE c.idCategorie IN :ids";

    @Autowired
    private EntityManager entityManager;


    @Override
    public Collection<CategorieSupprime> findParents(Map<Integer, CategorieSupprime> cats) {

        if (!cats.isEmpty()) {
            // Construire la requête
            String sql = "SELECT p FROM CategorieSupprime AS p WHERE ";

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
            TypedQuery<CategorieSupprime> query = entityManager.createQuery(sql, CategorieSupprime.class);

            return query.getResultList();
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public int findBorneMax() {

        Query query;
        query = entityManager.createQuery(SQL_BORNE_MAX);

        Integer a = (Integer) query.getSingleResult();

        return a;
    }


    @Override
    public void changerBornesEtLevel(List<Integer> ids, int intervalleDeDeplacement, int intervalLevel) {
        Query query = entityManager.createQuery(SQL_CHANGER_BORNES_ET_LEVEL);
        query.setParameter("nl", intervalLevel);
        query.setParameter("ids", ids);
        query.setParameter("depl", intervalleDeDeplacement);
        query.executeUpdate();
        refreshEveryCategoriesSupprime();

    }

    private void refreshEveryCategoriesSupprime() {
        int i = 1;
        CategorieSupprime categorieSupprimer = entityManager.find(CategorieSupprime.class, i);
        while (categorieSupprimer != null) {
            entityManager.refresh(categorieSupprimer);
            i = i + 1;
            categorieSupprimer = entityManager.find(CategorieSupprime.class, i);
        }
    }

}
