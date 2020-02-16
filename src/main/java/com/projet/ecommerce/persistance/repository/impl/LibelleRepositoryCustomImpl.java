package com.projet.ecommerce.persistance.repository.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.projet.ecommerce.persistance.entity.Libelle;
import com.projet.ecommerce.persistance.repository.LibelleRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LibelleRepositoryCustomImpl implements LibelleRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Collection<Libelle> findByNomIgnoringCase(String nom) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Libelle> libelleCriteriaQuery = criteriaBuilder.createQuery(Libelle.class);
        Root<Libelle> libelleRoot = libelleCriteriaQuery.from(Libelle.class);

        libelleCriteriaQuery
            .select(libelleRoot)
            .where(criteriaBuilder.like(
                criteriaBuilder.upper(libelleRoot.get("nom")),
                nom.toUpperCase()));

        //return entityManager.createQuery(libelleCriteriaQuery).getResultList();
        return null;
    }
}