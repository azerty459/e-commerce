package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class ProduitRepositoryCustomImpl implements ProduitRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Collection<Produit> findAllWithCriteria(String ref, String cat) {

        String sql = "";
        Query query = null;

        if(ref != null) {
            sql += "SELECT p FROM Produit p WHERE p.referenceProduit = :ref"; // Java Persistence Query Language
            query =  entityManager.createQuery(sql, Produit.class);
            query.setParameter("ref", ref);
        }

        return query.getResultList();

    }

}
