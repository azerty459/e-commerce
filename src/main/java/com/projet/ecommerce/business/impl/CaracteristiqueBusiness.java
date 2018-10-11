package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Autowired
    private CaracteristiqueRepositoryCustom caracteristiqueRepositoryCustom;

    @Override
    public CaracteristiqueDTO add(CaracteristiqueDTO caracteristiqueDTO) {
        // Renvoi d'erreurs
        if (caracteristiqueDTO == null) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de la caractéristique : l'object Caractéristique ne peut pas être null.");
            throw graphQLCustomException;
        } else if (caracteristiqueDTO.getLabel().isEmpty()) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de la caractéristique : l'un des attributs spécifié est null.");
            graphQLCustomException.ajouterExtension("label", caracteristiqueDTO.getLabel());
            throw graphQLCustomException;
        } else if (caracteristiqueDTO.getId() != null) {
            Optional<Caracteristique> caracteristiqueOptional = caracteristiqueRepository.findById(caracteristiqueDTO.getId());
            if (caracteristiqueOptional.isPresent()) {
                throw new GraphQLCustomException("La caractéristique avec cet id existe déjà");
            }
        }
        /*
        // TODO debug caracteristiqueRepositoryCustom.findByLabel
        else {
            Optional<Caracteristique> caracteristiqueOptional = caracteristiqueRepositoryCustom.findByLabel(caracteristiqueDTO.getLabel());
            if (caracteristiqueOptional.isPresent()) {
                throw new GraphQLCustomException("Erreur dans l'ajout de la caractéristique : La caractéristique avec ce label existe déjà.");
            }

        }*/

        // Si pas d'erreur
        Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO);
        caracteristiqueRepository.save(caracteristique);

        return caracteristiqueDTO;
        // TODO à remplacer par la ligne ci-dessous losque les fonctions get auront été implémentées
        // return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
    }

    @Override
    public boolean delete(int idCaracteristique) {

        Optional<Caracteristique> caracteristiqueOptional = caracteristiqueRepository.findById(idCaracteristique);
        //caracteristiqueOptional.orElseThrow(() -> {throw new GraphQLCustomException("Aucune caractéristique attaché à cet id.");});

        try {
            caracteristiqueRepository.deleteById(idCaracteristique);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<CaracteristiqueDTO> getAll() {
        List<Caracteristique> caracteristiqueList = new ArrayList<>();
        caracteristiqueRepository.findAll().forEach(caracteristiqueList::add);
        return new ArrayList<>(CaracteristiqueTransformer.entityToDto(caracteristiqueList));
    }

}
