package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;

import java.util.Collection;

public interface PhotoRepositoryCustom {

    Collection<Photo> findAllWithCriteria(String ref);
}
