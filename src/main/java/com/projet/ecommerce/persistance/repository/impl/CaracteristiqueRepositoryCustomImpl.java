package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.*;
import com.projet.ecommerce.persistance.entity.Caracteristique_;
import com.projet.ecommerce.persistance.entity.Libelle_;
import com.projet.ecommerce.persistance.entity.Produit_;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.Collections;

public class CaracteristiqueRepositoryCustomImpl implements CaracteristiqueRepositoryCustom {
    @Autowired
    EntityManager entityManager;
    
    @Override
    public Collection<Caracteristique> findByProduitAndMotCle(Produit produit, String motCle) {
        if (motCle == null) motCle = "";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Caracteristique> caracteristiqueCriteriaQuery = criteriaBuilder.createQuery(Caracteristique.class);
        Root<Caracteristique> caracteristiqueRoot = caracteristiqueCriteriaQuery.from(Caracteristique.class);

        Predicate concerneLeProduit;
        if (produit == null || produit.getReferenceProduit()==null) {
            //Dans ce cas on met le predicat concerneLeProduit à true
            concerneLeProduit = criteriaBuilder.and();
        } else {
            concerneLeProduit = criteriaBuilder.equal(
                    caracteristiqueRoot.get(Caracteristique_.produit).get(Produit_.referenceProduit),
                    produit.getReferenceProduit());
        }

        Predicate valeurContainsMotCle = criteriaBuilder.like(
                criteriaBuilder.upper(caracteristiqueRoot.get(Caracteristique_.valeur)),
                "%" + motCle.toUpperCase() + "%");
        Predicate libelleNomContainsMotCle = criteriaBuilder.like(
                criteriaBuilder.upper(caracteristiqueRoot.get(Caracteristique_.libelle).get(Libelle_.nom)),
                "%" + motCle.toUpperCase() + "%");
        Predicate contientLeMotCle = criteriaBuilder.or(valeurContainsMotCle,libelleNomContainsMotCle);

        Predicate concerneLeProduitEtContientLeMotCle = criteriaBuilder.and(concerneLeProduit,contientLeMotCle);

        caracteristiqueCriteriaQuery.select(caracteristiqueRoot).where(concerneLeProduitEtContientLeMotCle);

        return entityManager.createQuery(caracteristiqueCriteriaQuery).getResultList();
    }
}
