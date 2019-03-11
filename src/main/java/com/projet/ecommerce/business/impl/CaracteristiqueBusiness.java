package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IAvisClientBusiness;
import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.AvisClientTransformer;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.AvisClientRepository;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.projet.ecommerce.utilitaire.Utilitaire.mergeObjects;

@Service
public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {

	@Autowired
	private TypeCaracteristiqueRepository typeCaracteristiqueRepository;
	
	@Autowired
	private CaracteristiqueRepository caracteristiqueRepository;
	
    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Retourne la liste des avis clients d'un produit
     *
     * @param ref la référence du produit recherché
     * @return la liste des avis clients du produit
     */
    @Override
    public Collection<CaracteristiqueDTO> getAll(String ref) {

        Collection<Caracteristique> caracteristiques = new ArrayList<>();
        if (ref != null) {
        	caracteristiques.addAll(caracteristiqueRepository.findByProduit(produitRepository.findById(ref).orElse(null)));
        }
        return CaracteristiqueTransformer.entityToDto(caracteristiques);
    }

    @Override
    public CaracteristiqueDTO add(CaracteristiqueDTO caracteristique, String ref) {
        if (caracteristique == null || caracteristique.getTypeCaracteristiqueDTO() == null
        		|| caracteristique.getValeur().isEmpty()  || ref.isEmpty()) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de la caractéristique(la référence produit, la valeur ou le type de caractéristique peut être nul)");
            graphQLCustomException.ajouterExtension("type", Integer.toString(caracteristique.getTypeCaracteristiqueDTO().getId()));
            graphQLCustomException.ajouterExtension("valeur",caracteristique.getValeur());
            graphQLCustomException.ajouterExtension("ref",ref);
            throw graphQLCustomException;
        }
        if (caracteristiqueRepository.findById(Integer.toString(caracteristique.getId())).isPresent()) {
			throw new GraphQLCustomException("Le produit à ajouter existe déjà.");
		}

        Optional<Produit> produitOptional = produitRepository.findById(ref);
        produitOptional.orElseThrow(() -> new GraphQLCustomException("Le produit pour cette caractéristique n'existe pas."));
       
        Optional<TypeCaracteristique> typeCaracteristiqueOptional = typeCaracteristiqueRepository.findById(Integer.toString(caracteristique.getTypeCaracteristiqueDTO().getId()));
        typeCaracteristiqueOptional.orElseThrow(() -> new GraphQLCustomException("Le type pour cette caractéristique n'existe pas."));
        
        Caracteristique caracteristique2 = CaracteristiqueTransformer.dtoToEntity(caracteristique);
        caracteristique2.setProduit(produitOptional.orElse(null));
        caracteristique2.setTypeCaracteristique(typeCaracteristiqueOptional.orElse(null));
        return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique2));
    }

}

