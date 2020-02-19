package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.persistance.entity.*;
import com.projet.ecommerce.persistance.entity.AvisClient_;
import com.projet.ecommerce.persistance.entity.Caracteristique_;
import com.projet.ecommerce.persistance.entity.Libelle_;
import com.projet.ecommerce.persistance.entity.Produit_;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;


@Repository
public class ProduitRepositoryCustomImpl implements ProduitRepositoryCustom {

    // Requêtes en Java Persistence Query Language
    private static final String SQL_ALL_PRODUCTS = "SELECT p FROM Produit AS p";
    private static final String SQL_PRODUCT_BY_REFERENCE = "SELECT p FROM Produit AS p WHERE p.referenceProduit = :ref";

    private static final String SQL_PRODUCTS_BY_CATEGORY = "SELECT c.produits FROM Categorie AS c " +
            "WHERE c.borneGauche >= (" +
            "SELECT cat_recherchee.borneGauche " +
            "FROM Categorie AS cat_recherchee " +
            "WHERE cat_recherchee.nomCategorie =:cat" +
            ") " +
            "AND c.borneDroit <= (" +
            "SELECT cat_recherchee2.borneDroit " +
            "FROM Categorie AS cat_recherchee2 " +
            "WHERE cat_recherchee2.nomCategorie =:cat)";

    @Autowired
    private EntityManager entityManager;

    @Override
    public Collection<Produit> findAllWithCriteria(String ref, String cat) {

        Query query = null;

        if (ref == null) {
            if (cat == null) {
                query = entityManager.createQuery(SQL_ALL_PRODUCTS, Produit.class);
            } else {
                System.out.println("testsdfsdfsdfsdf");
                query = entityManager.createQuery(SQL_PRODUCTS_BY_CATEGORY, Collection.class);
                query.setParameter("cat", cat);
            }
        } else {
            // Dans tous les cas, on fait une recherche par référence
            query = entityManager.createQuery(SQL_PRODUCT_BY_REFERENCE, Produit.class);
            query.setParameter("ref", ref);
        }
        System.out.println(query.getResultList().size());
        return query.getResultList();
    }

    @Override
    public Collection<Produit> findByCriteria(Float minAvgNote, Float maxAvgNote, String nomProduit, String motCle, Categorie categorie) {
        //On crée une requête qui retourne des produits depuis la table pour l'entité Produit
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> produitCriteriaQuery = criteriaBuilder.createQuery(Produit.class);
        Root<Produit> produitRoot = produitCriteriaQuery.from(Produit.class);

        //On crée une sous requete qui renvoie un double (la moyenne des notes des avis pour un produit donné)
        //depuis la table pour l'entité AvisClient
        Subquery<Double> avgNoteSubquery = produitCriteriaQuery.subquery(Double.class);
        Root<AvisClient> avisClientSubRoot = avgNoteSubquery.from(AvisClient.class);
        avgNoteSubquery.where(criteriaBuilder.equal(produitRoot,avisClientSubRoot.get(AvisClient_.produit)));
        avgNoteSubquery.select(criteriaBuilder.avg(avisClientSubRoot.get(AvisClient_.note)));

        //Définitions des différents prédicats
        Predicate aboveMinAvgNote;
        if (minAvgNote == null) {
            //Dans ce cas on met le predicat aboveMinAvgNote à true
            aboveMinAvgNote = criteriaBuilder.and();
        } else {
            aboveMinAvgNote = criteriaBuilder.gt(avgNoteSubquery,minAvgNote);
        }

        Predicate belowMaxAvgNote;
        if (maxAvgNote == null) {
            //idem
            belowMaxAvgNote = criteriaBuilder.and();
        } else {
            belowMaxAvgNote = criteriaBuilder.lt(avgNoteSubquery,maxAvgNote);
        }

        Predicate hasRightName;
        if (nomProduit == null) {
            hasRightName = criteriaBuilder.and();
        } else {
            //Case insensitive
            hasRightName = criteriaBuilder.like(criteriaBuilder.upper(produitRoot.get(Produit_.nom)), nomProduit.toUpperCase());
        }

        Predicate matchMotCle;
        if (motCle == null) {
            matchMotCle = criteriaBuilder.and();
        } else {
            matchMotCle = criteriaBuilder.like(criteriaBuilder.upper(produitRoot.get(Produit_.nom)), "%" + motCle.toUpperCase() + "%");
        }

        Predicate matchCategorie;
        if (categorie == null) {
            matchCategorie = criteriaBuilder.and();
        } else {
            matchCategorie = criteriaBuilder.isMember(categorie, produitRoot.get(Produit_.categories));
        }

        //Finalisation et éxécution
        Predicate isAMatch = criteriaBuilder.and(aboveMinAvgNote,belowMaxAvgNote, hasRightName, matchMotCle, matchCategorie);

        produitCriteriaQuery.select(produitRoot).where(isAMatch);

        return entityManager.createQuery(produitCriteriaQuery).getResultList();
    }

    //TODO delete this method
    @Override
    public void printAvgNoteByProduct() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> produitCriteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Produit> produitRoot = produitCriteriaQuery.from(Produit.class);

        Subquery<Double> avgNoteSubquery = produitCriteriaQuery.subquery(Double.class);
        Root<AvisClient> avisClientSubRoot = avgNoteSubquery.from(AvisClient.class);
        avgNoteSubquery.where(criteriaBuilder.equal(produitRoot,avisClientSubRoot.get(AvisClient_.produit)));
        avgNoteSubquery.select(criteriaBuilder.avg(avisClientSubRoot.get(AvisClient_.note)));

        produitCriteriaQuery.multiselect(produitRoot, avgNoteSubquery.getSelection());

        entityManager.createQuery(produitCriteriaQuery).getResultList().forEach(
                t->{
                    System.out.print(t.get(0,Produit.class).getNom() + " note " + t.get(1));
                }
        );
        return;
    }
}
