package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.persistance.entity.AvisClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AvisClientTransformer {

    private AvisClientTransformer() {
    }

    /**
     * Transforme une collection d'objets {@link AvisClientDTO} en une collection d'objets {@link AvisClient}.
     *
     * @param avisClientDTO Une collection d'objets AvisClientDTO
     * @return une collection d'objets AvisClient
     */
    public static Collection<AvisClient> dtoToEntity(Collection<AvisClientDTO> avisClientDTO) {
        List<AvisClient> listeAvisClients = new ArrayList<>();
        for (AvisClientDTO dtoAvisClient : avisClientDTO) {
            listeAvisClients.add(dtoToEntity(dtoAvisClient));
        }
        return listeAvisClients;
    }

    /**
     * Transforme un objet {@link AvisClientDTO} en un objet {@link AvisClient}.
     *
     * @param dtoAvisClient Un objet AvisClientDTO
     * @return un objet AvisClient
     */
    public static AvisClient dtoToEntity(AvisClientDTO dtoAvisClient) {
        AvisClient avisClient = new AvisClient();
        avisClient.setIdAvis(dtoAvisClient.getId());
        avisClient.setDateAvis(dtoAvisClient.getDate());
        avisClient.setDescription(dtoAvisClient.getDescription());
        avisClient.setNote(dtoAvisClient.getNote());
        return avisClient;
    }

    /**
     * Transforme une collection d'objets {@link AvisClient} en une collection d'objets {@link AvisClientDTO}.
     *
     * @param avisClients Une collection d'objets AvisClient
     * @return une collection d'objets AvisClientDTO
     */
    public static Collection<AvisClientDTO> entityToDto(Collection<AvisClient> avisClients) {
        List<AvisClientDTO> listeAvisClientsDTO = new ArrayList<>();
        for (AvisClient unAvisClient : avisClients) {
            listeAvisClientsDTO.add(entityToDto(unAvisClient));
        }
        return listeAvisClientsDTO;
    }

    /**
     * Transforme un objet {@link AvisClient} en {@link AvisClientDTO}
     *
     * @param avisClient Un objet AvisClient
     * @return un objet AvisClientDTO
     */
    public static AvisClientDTO entityToDto(AvisClient avisClient) {
        AvisClientDTO avisClientDTO = new AvisClientDTO();
        avisClientDTO.setId(avisClient.getIdAvis());
        avisClientDTO.setDate(avisClient.getDateAvis());
        avisClientDTO.setDescription(avisClient.getDescription());
        avisClientDTO.setNote(avisClient.getNote());
        return avisClientDTO;
    }
}
