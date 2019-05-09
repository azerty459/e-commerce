package com.projet.ecommerce.business.impl;

import static com.projet.ecommerce.utilitaire.Utilitaire.mergeObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ecommerce.business.IAvisClientBusiness;
import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.business.dto.transformer.AvisClientTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.AvisClientRepository;
import com.projet.ecommerce.persistance.repository.AvisClientRepositoryCustom;
import com.projet.ecommerce.persistance.repository.ProduitRepository;

@Service
public class AvisClientBusiness implements IAvisClientBusiness {

    @Autowired
    private AvisClientRepository avisClientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Retourne la liste des avis clients d'un produit
     *
     * @param ref la référence du produit recherché
     * @return la liste des avis clients du produit
     */
    @Override
    public Collection<AvisClientDTO> getAll(String ref) {

        Collection<AvisClient> avisClients = new ArrayList<>();
        if (ref == null) {
            avisClients.addAll(avisClientRepository.findAll());
        } else {
            avisClients.addAll(avisClientRepository.findByProduit_ReferenceProduit(ref));
        }
        return AvisClientTransformer.listEntityToDto(avisClients);
    }

    @Override
    public AvisClientDTO update(AvisClientDTO avisClient) {
        if (avisClient == null) {
            return null;
        }

        Optional<AvisClient> optionalAvisClient = avisClientRepository
                .findById(avisClient.getId());

        optionalAvisClient.orElseThrow(() -> new GraphQLCustomException("L'avis client recherché n'existe pas."));

        AvisClient retourAvisClient = optionalAvisClient.get();

        AvisClient avis = AvisClientTransformer.dtoToEntity(avisClient);
        // On fusionne les deux produits en un
        AvisClient avisClientFinal = null;
        try {
            avisClientFinal = mergeObjects(avis, retourAvisClient);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new GraphQLCustomException("L'avis client ne peut pas être sauvegardé");
        }

        // On retourne l'avis client final et on le transforme en DTO
        return AvisClientTransformer.entityToDto(avisClientRepository.save(avisClientFinal));
    }

    @Override
    public boolean delete(Integer idAvisClient) {
        if (idAvisClient == null) {
            return false;
        }
        Optional<AvisClient> optionalAvisClient = avisClientRepository.findById(idAvisClient);
        optionalAvisClient.orElseThrow(() -> new GraphQLCustomException("L'avis client recherché n'existe pas."));
        avisClientRepository.deleteById(idAvisClient);
        return true;
    }

    @Override
    public AvisClientDTO add(AvisClientDTO avisClient) {
        if (avisClient == null || avisClient.getRefProduit().isEmpty()
                || avisClient.getDescription().isEmpty() || avisClient.getNote() < 0) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout du produit (la référence produit, la description et la note ne peut être null)");
            graphQLCustomException.ajouterExtension("description", avisClient.getDescription());
            graphQLCustomException.ajouterExtension("note", avisClient.getNote() + "");
            graphQLCustomException.ajouterExtension("referenceProduit", avisClient.getRefProduit() + "");
            throw graphQLCustomException;
        }

        Optional<Produit> produitOptional = produitRepository.findById(avisClient.getRefProduit());
        produitOptional.orElseThrow(() -> new GraphQLCustomException("Le produit pour cet avis n'existe pas."));
        AvisClient avis = AvisClientTransformer.dtoToEntity(avisClient);
        return AvisClientTransformer.entityToDto(avisClientRepository.save(avis));
    }

    /**
     * Signature supplémentaire du getAll pour éviter de passer un null explicitement en paramètre lors de l'appel
     *
     * @return la liste de tous les avis clients
     */
    public Collection<AvisClientDTO> getAll() {
        return getAll(null);
    }

	@Override
	public double getAverageByReference(String ref) {
		
		return avisClientRepository.averageByReferenceProduit(ref);
	}

}

