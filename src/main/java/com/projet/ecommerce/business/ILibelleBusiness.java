package com.projet.ecommerce.business;

import java.util.Collection;

import javax.annotation.Nullable;

import com.projet.ecommerce.business.dto.LibelleDTO;

/**
 * De quelles méthodes peut-on avoir besoin à propos des libellés ?
 * ajouter un libellé
 * obtenir un libellé par l'id
 * obtenir une collection de libellé par mot-clé
 * éditer un libellé
 * supprimer un libellé
 */
public interface ILibelleBusiness {
    LibelleDTO add(String nom);
    LibelleDTO get(int idLibelle);
    Collection<LibelleDTO> getAll(@Nullable String motCle);
    LibelleDTO update(int idLibelleExistant, String nouveauNom);
    void delete(int idLibelle);
}