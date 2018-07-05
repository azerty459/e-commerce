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

import static com.projet.ecommerce.utilitaire.utilitaire.mergeObjects;

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
        if(ref == null){
            avisClients.addAll(avisClientRepository.findAll());
        }else{
            avisClients.addAll(avisClientRepository.findByProduit_ReferenceProduit(ref));
        }
        return new ArrayList<>(AvisClientTransformer.listEntityToDto(avisClients));
    }

    @Override
    public AvisClientDTO update(AvisClientDTO avisClient) {
        if (avisClient == null) {
            return null;
        }

        AvisClient retourAvisClient = this.getAvisClientExistant(avisClient.getId());
        AvisClient avis = AvisClientTransformer.dtoToEntity(avisClient);
        // On fusionne les deux produits en un
        AvisClient avisClientFinal = null;
        try {
            avisClientFinal = mergeObjects(avis, retourAvisClient);
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
        if(idAvisClient == null){
            return false;
        }
        this.getAvisClientExistant(idAvisClient);
        avisClientRepository.deleteById(idAvisClient);
        return true;
    }

    @Override
    public AvisClientDTO add(AvisClientDTO avisClient) {
        if (avisClient == null || avisClient.getRefProduit().isEmpty()
                || avisClient.getDescription().isEmpty() || avisClient.getNote() <= 0) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout du produit (la référence produit, la description et la note ne peut être null)");
            graphQLCustomException.ajouterExtension("description", avisClient.getDescription());
            graphQLCustomException.ajouterExtension("note", avisClient.getNote() +"");
            graphQLCustomException.ajouterExtension("referenceProduit", avisClient.getRefProduit() +"");
            throw graphQLCustomException;
        }

        this.getProduit(avisClient.getRefProduit());
        AvisClient avis = AvisClientTransformer.dtoToEntity(avisClient);
        return AvisClientTransformer.entityToDto(avisClientRepository.save(avis));
    }

    /**
     * Signature supplémentaire du getAll pour éviter de passer un null explicitement en paramètre lors de l'appel
     * @return la liste de tous les avis clients
     */
    public List<AvisClientDTO> getAll() {
        return this.getAll(null);
    }

    /**
     * Récupère un produit à partir de sa référence
     * @param refProduit
     * @return
     */
    private Produit getProduit(String refProduit){
        // Récupération du produit
        Optional<Produit> produitOptional = produitRepository.findById(refProduit);
        if (!produitOptional.isPresent()) {
            throw new GraphQLCustomException("Le produit pour cet avis n'existe pas.");
        }

       return produitOptional.get();
    }

    private AvisClient getAvisClientExistant(Integer idAvisClient){
        Optional<AvisClient> optionalAvisClient = avisClientRepository.findById(idAvisClient);
        if (!optionalAvisClient.isPresent()) {
            throw new GraphQLCustomException("L'avis client recherché n'existe pas.");
        }

        return optionalAvisClient.get();
    }

}

