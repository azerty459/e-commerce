package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiqueID;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Autowired
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    /**
     * Ajoute une caractéristique au produit en BD
     * @param referenceProduit
     * @param typeCaracteristique
     * @param valeurCaracteristique
     * @return
     */
    @Override
    public CaracteristiqueDTO addCaracteristique(String referenceProduit, String typeCaracteristique, String valeurCaracteristique) {
        if (StringUtils.isEmpty(referenceProduit) || StringUtils.isEmpty(typeCaracteristique) || StringUtils.isEmpty(valeurCaracteristique)) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de la caractéristique (la référence du produit, le type et la valeur ne peuvent être null)");
            graphQLCustomException.ajouterExtension("Référence", referenceProduit);
            graphQLCustomException.ajouterExtension("Type", typeCaracteristique);
            graphQLCustomException.ajouterExtension("Valeur", valeurCaracteristique + "");
            throw graphQLCustomException;
        }

        CaracteristiqueID idCaracteristique = new CaracteristiqueID(referenceProduit, typeCaracteristique);
        if (caracteristiqueRepository.findById(idCaracteristique).isPresent()) {
            throw new GraphQLCustomException("Le produit possède déjà ce type de caractéristique");
        }

        if(!typeCaracteristiqueRepository.existsById(typeCaracteristique)){
            throw new GraphQLCustomException("Ce type de caractéristique n'est pas recensé");
        }

        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setReferenceProduit(referenceProduit);
        caracteristique.setType(typeCaracteristique);
        caracteristique.setValeur(valeurCaracteristique);
        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }

    /**
     * Supprime la caractéristique du produit en BD
     * @param referenceProduit reférence du produit dont la caractéristique sera supprimé
     * @param typeCaracteristique type de la caractéristique à supprimer
     * @return
     */
    @Override
    public boolean deleteCaracteristique(String referenceProduit, String typeCaracteristique) {
        CaracteristiqueID idCaracteristique = new CaracteristiqueID(referenceProduit, typeCaracteristique);
        caracteristiqueRepository.deleteById(idCaracteristique);
        return true;
    }

    /**
     * Remplace la valeur d'une caractéristique d'un produit en BD
     * @param caracteristique nouvelle caracteristique
     * @return
     */
    @Override
    public CaracteristiqueDTO updateCaracteristique(Caracteristique caracteristique) {
        if (caracteristique == null || StringUtils.isEmpty(caracteristique.getReferenceProduit())
                || StringUtils.isEmpty(caracteristique.getType()) || StringUtils.isEmpty(caracteristique.getValeur())) {
            return null;
        }

        CaracteristiqueID idCaracteristique = new CaracteristiqueID(caracteristique.getReferenceProduit(), caracteristique.getType());
        if(!caracteristiqueRepository.existsById(idCaracteristique)){
            throw new GraphQLCustomException("Le produit ne possède pas ce type de caractéristique");
        }

        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }
}
