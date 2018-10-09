package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Override
    public CaracteristiqueDTO add(CaracteristiqueDTO caracteristiqueDTO) {
        if (caracteristiqueDTO == null) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de la caractéristique : l'object Caractéristique ne peut pas être null.");
            throw graphQLCustomException;
        } else if (caracteristiqueDTO.getLabel().isEmpty()) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de la caractéristique : l'un des attributs spécifié est null.");
            graphQLCustomException.ajouterExtension("label", caracteristiqueDTO.getLabel());
            throw graphQLCustomException;
        }
        /*
        Optional<Caracteristique> caracteristiqueOptional = caracteristiqueRepository.findById(caracteristiqueDTO.getId());
        caracteristiqueOptional.ifPresent(() -> {throw new GraphQLCustomException("La caractéristique avec cet id existe déjà")});
        */

        /*
        Optional<Caracteristique> caracteristiqueOptional = caracteristiqueRepository.findByLabel(caracteristiqueDTO.getLabel());
        caracteristiqueOptional.ifPresent(() -> {throw new GraphQLCustomException("La caractéristique avec ce label existe déjà")});
        */

        Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO);
        caracteristiqueRepository.save(caracteristique);

        return caracteristiqueDTO;
        // TODO à remplacer par la ligne ci-dessous losque les fonctions get auront été implémentées
        // return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }

}
