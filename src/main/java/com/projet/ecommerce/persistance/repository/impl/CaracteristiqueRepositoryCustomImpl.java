package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class CaracteristiqueRepositoryCustomImpl implements CaracteristiqueRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    public Optional<Caracteristique> findByLabel(String label) {
        TypedQuery<Caracteristique> query = entityManager.createQuery("SELECT c FROM Caracteristique AS c WHERE c.label = :label", Caracteristique.class);
        query.setParameter("label", label);
        return Optional.of(query.getSingleResult());
    }

}
