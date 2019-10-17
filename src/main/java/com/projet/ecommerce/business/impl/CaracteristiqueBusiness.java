package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiqueId;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;


    /**
     * Ajoute une caractéristique au produit dans la base de donnée.
     * @param referenceProduit
     * @param typeCaracteristique
     * @param valeurCaracteristique
     * @return
     */
    @Override
    public CaracteristiqueDTO addCaracteristique(String referenceProduit, String typeCaracteristique, String valeurCaracteristique) {
        if (StringUtils.isEmpty(referenceProduit) || StringUtils.isEmpty(typeCaracteristique) || StringUtils.isEmpty(valeurCaracteristique)) {
        	throw new GraphQLCustomException("Erreur dans l'ajout de la caractéristique (la référence du produit, le type et la valeur ne peuvent être null)");
        }

        CaracteristiqueId idCaracteristique = new CaracteristiqueId(referenceProduit, typeCaracteristique);
        if (caracteristiqueRepository.findById(idCaracteristique).isPresent()) {
            throw new GraphQLCustomException("Le produit possède déjà ce type de caractéristique");
        }

        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setReferenceProduit(referenceProduit);
        caracteristique.setTypeCaracteristique(typeCaracteristique);
        caracteristique.setValeurCaracteristique(valeurCaracteristique);
        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }
    
    /**
     * Remplace la valeur d'une caractéristique d'un produit dans la base de donnée.
     * @param caracteristique nouvelle caracteristique
     * @return
     */
    @Override
    public CaracteristiqueDTO updateCaracteristique(Caracteristique caracteristique) {
        if (caracteristique == null || StringUtils.isEmpty(caracteristique.getReferenceProduit())
                || StringUtils.isEmpty(caracteristique.getTypeCaracteristique()) || StringUtils.isEmpty(caracteristique.getValeurCaracteristique())) {
            return null;
        }

        CaracteristiqueId idCaracteristique = new CaracteristiqueId(caracteristique.getReferenceProduit(), caracteristique.getTypeCaracteristique());
        if(!caracteristiqueRepository.existsById(idCaracteristique)){
            throw new GraphQLCustomException("Le produit ne possède pas ce type de caractéristique");
        }

        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }

    /**
     * Supprime la caractéristique du produit donnée en paramètre de la base de donnée.
     * @param referenceProduit reférence du produit dont la caractéristique sera supprimé
     * @param typeCaracteristique type de la caractéristique à supprimer
     * @return
     */
    @Override
    public boolean deleteCaracteristique(String referenceProduit, String typeCaracteristique) {
        if (StringUtils.isEmpty(referenceProduit) || StringUtils.isEmpty(typeCaracteristique)) {
            return false;
        }
    	
    	CaracteristiqueId idCaracteristique = new CaracteristiqueId(referenceProduit, typeCaracteristique);
        caracteristiqueRepository.deleteById(idCaracteristique);
        return true;
    }

}
