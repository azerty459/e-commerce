package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface du service CategorieBusiness.
 */

public interface IRoleBusiness {

    /**
     * Méthode allant chercher les catégories
     *
     * @param id  l'id du rôle recherché. "null" si on cherche à lister toutes les catégories.
     * @param nom le nom du rôle à aller chercher. "null" si on cherche à lister toutes les catégories.
     * @return liste des rôles recherchées
     */
    List<RoleDTO> getRole(int id, String nom);

    /**
     * Méthode définissant l'ajout d'un utilisateur.
     *
     * @param roleDTO L'objet RoleDTO à sauvegarder
     * @return l'objet rôle ajouté
     */
    UtilisateurDTO add(RoleDTO roleDTO);

    /**
     * Méthode définissant la modification d'un utilisateur.
     *
     * @param roleDTO L'objet rôle modifié à sauvegarder
     * @return l'objet utilisateur modifié
     */
    RoleDTO update(RoleDTO roleDTO);

    /**
     * Méthode définissant la suppression d'un utilisateur.
     *
     * @param id Id de l'utilisateur à supprimer
     * @return true
     */
    boolean delete(int id);

    /**
     * Méthode définissant la pagination
     *
     * @param pageNumber la page souhaitée
     * @param nb         le nombre de role à afficher dans la page
     * @return une page d'utilisateur
     */
    Page<RoleDTO> getPage(int pageNumber, int nb);
}
