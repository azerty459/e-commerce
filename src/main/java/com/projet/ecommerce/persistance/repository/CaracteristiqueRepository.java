package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Libelle;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.primarykey.CaracteristiqueID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettant de communiquer avec la base de données pour la table Caractéristique.
 */

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, CaracteristiqueID>, CaracteristiqueRepositoryCustom {
    //XXX - findById_ReferenceProduit ou findByProduit ?
    Collection<Caracteristique> findById_ReferenceProduit(String refProduit);
    Collection<Caracteristique> findByProduit(Produit produit);
    Collection<Caracteristique> findByProduitAndLibelle(Produit produit, Libelle libelle);


}
