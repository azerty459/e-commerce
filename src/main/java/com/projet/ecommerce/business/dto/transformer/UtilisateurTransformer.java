package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.persistance.entity.Utilisateur;

public class UtilisateurTransformer {

    private UtilisateurTransformer() {
    }

    /**
     * Transforme une collection d'objets Utilisateur en une collection d'objets UtilisateurDTO.
     *
     * @param utilisateurCollection Une collection d'objets utilisateur
     * @return une collection d'objet ProduitDTO
     */
    public static Collection<UtilisateurDTO> entityToDto(Collection<Utilisateur> utilisateurCollection) {
        if (utilisateurCollection == null) {
            return null;
        }
        List<UtilisateurDTO> utilisateurDTOList = new ArrayList<>();
        for (Utilisateur utilisateur : utilisateurCollection) {
            utilisateurDTOList.add(entityToDto(utilisateur));
        }
        return utilisateurDTOList;
    }

    /**
     * Transforme un objet Utilisateur en UtilisateurDTO
     *
     * @param utilisateur Un objet Utilisateur
     * @return une objet UtilisateurDTO
     */
    public static UtilisateurDTO entityToDto(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setEmail(utilisateur.getEmail());
        utilisateurDTO.setId(utilisateur.getId());
        utilisateurDTO.setMdp(utilisateur.getMdp());
        utilisateurDTO.setNom(utilisateur.getNom());
        utilisateurDTO.setPrenom(utilisateur.getPrenom());
        utilisateurDTO.setRole(RoleTransformer.entityToDto(utilisateur.getRole()));
        return utilisateurDTO;
    }

    /**
     * Transforme une collection d'objets Utilisateur en une collection d'objets UtilisateurDTO.
     *
     * @param utilisateurDTOCollection Une collection d'objets UtilisateurDTO
     * @return une collection d'objet Utilisateur
     */
    public static Collection<Utilisateur> dtoToEntity(Collection<UtilisateurDTO> utilisateurDTOCollection) {
        if (utilisateurDTOCollection == null) {
            return null;
        }
        List<Utilisateur> utilisateurs = new ArrayList<>();
        for (UtilisateurDTO utilisateurDTO : utilisateurDTOCollection) {
            utilisateurs.add(dtoToEntity(utilisateurDTO));
        }
        return utilisateurs;
    }

    /**
     * Transforme un objet UtilisateurDTO en Utilisateur
     *
     * @param utilisateurDTO Un objet UtilisateurDTO
     * @return une objet Utilisateur
     */
    public static Utilisateur dtoToEntity(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(utilisateurDTO.getNom());
        utilisateur.setId(utilisateurDTO.getId());
        utilisateur.setMdp(utilisateurDTO.getMdp());
        utilisateur.setPrenom(utilisateurDTO.getPrenom());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setRole(RoleTransformer.dtoToEntity(utilisateurDTO.getRole()));
        return utilisateur;
    }
}
