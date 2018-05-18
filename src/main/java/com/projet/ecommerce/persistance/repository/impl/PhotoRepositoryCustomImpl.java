package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.repository.PhotoRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;


@Repository
public class PhotoRepositoryCustomImpl implements PhotoRepositoryCustom {

    // Requêtes en Java Persistence Query Language
    private static final String SQL_ALL_PHOTOS = "SELECT p FROM Photo AS p";
    private static final String SQL_PHOTO_BY_REFERENCE = "SELECT p FROM Photo AS p WHERE p.produit.referenceProduit = :ref";

    @Autowired
    private EntityManager entityManager;

    @Override
    public Collection<Photo> findAllWithCriteria(String ref) {
        Query query = null;
        if(ref == null) {
            query =  entityManager.createQuery(SQL_ALL_PHOTOS, Photo.class);
        }else{
            query =  entityManager.createQuery(SQL_PHOTO_BY_REFERENCE, Photo.class);
            query.setParameter("ref", ref);
        }
        return query.getResultList();
    }
}