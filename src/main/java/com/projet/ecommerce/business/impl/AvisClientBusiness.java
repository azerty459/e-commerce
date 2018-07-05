package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IAvisClientBusiness;
import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.business.dto.transformer.AvisClientTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.AvisClientRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.projet.ecommerce.utilitaire.Utilitaire.mergeObjects;

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
    public List<AvisClientDTO> getAll(String ref) {

        Collection<AvisClient> avisClients = new ArrayList<>();
        if (ref == null) {
            avisClients.addAll(avisClientRepository.findAll());
        } else {
            avisClients.addAll(avisClientRepository.findByProduit_ReferenceProduit(ref));
        }
        return new ArrayList<>(AvisClientTransformer.entityToDto(avisClients));
    }

    @Override
    public AvisClientDTO update(AvisClient avisClient) {
        if (avisClient == null) {
            return null;
        }

        Optional<AvisClient> optionalAvisClient = avisClientRepository.findById(avisClient.getIdAvis());
        if (!optionalAvisClient.isPresent()) {
            throw new GraphQLCustomException("L'avis client recherché n'existe pas.");
        }

        // On fusionne les deux produits en un
        AvisClient retourAvisClient = optionalAvisClient.get();

        AvisClient avisClientFinal = null;
        try {
            avisClientFinal = mergeObjects(avisClient, retourAvisClient);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        if (avisClientFinal == null) {
            throw new GraphQLCustomException("L'avis client ne peut pas être sauvegardé");
        }

        // On retourne l'avis client final et on le transforme en DTO
        return AvisClientTransformer.entityToDto(avisClientRepository.save(avisClientFinal));
    }

    @Override
    public boolean delete(Integer idAvisClient) {
        avisClientRepository.deleteById(idAvisClient);
        return true;
    }

    @Override
    public AvisClientDTO add(String description, Integer note, String referenceProduit) {
        if (referenceProduit == null || description.isEmpty() || note == null) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout du produit (la référence produit, la description et la note ne peut être null)");
            graphQLCustomException.ajouterExtension("description", description);
            graphQLCustomException.ajouterExtension("note", note + "");
            graphQLCustomException.ajouterExtension("referenceProduit", referenceProduit + "");
            throw graphQLCustomException;
        }

        AvisClient avisClient = new AvisClient();
        avisClient.setDescription(description);
        avisClient.setNote(note);
        avisClient.setProduit(getProduit(referenceProduit));

        return AvisClientTransformer.entityToDto(avisClientRepository.save(avisClient));
    }

    /**
     * Signature supplémentaire du getAll pour éviter de passer un null explicitement en paramètre lors de l'appel
     *
     * @return la liste de tous les avis clients
     */
    public List<AvisClientDTO> getAll() {
        return getAll(null);
    }

    /**
     * Récupère un produit à partir de sa référence
     *
     * @param refProduit
     * @return
     */
    private Produit getProduit(String refProduit) {
        // Récupération du produit
        Optional<Produit> produitOptional = produitRepository.findById(refProduit);
        if (!produitOptional.isPresent()) {
            throw new GraphQLCustomException("Le produit pour cet avis n'existe pas.");
        }

        return produitOptional.get();
    }

}

