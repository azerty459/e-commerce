package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.persistance.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoleTransformer {

    private RoleTransformer() {
    }

    /**
     * Transforme une collection d'objets RoleDTO en une collection d'objets Role.
     *
     * @param roleCollection Une collection d'objets Role
     * @return une collection d'objet RoleDTO
     */
    public static Collection<RoleDTO> entityToDto(Collection<Role> roleCollection) {
        if (roleCollection == null) {
            return null;
        }
        List<RoleDTO> roleDTOArrayList = new ArrayList<>();
        for (Role role : roleCollection) {
            roleDTOArrayList.add(entityToDto(role));
        }
        return roleDTOArrayList;
    }

    /**
     * Transforme un objet Role en RoleDTO
     *
     * @param role Un objet Role
     * @return une objet RoleDTO
     */
    public static RoleDTO entityToDto(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setNom(role.getNom());
        return roleDTO;
    }

    /**
     * Transforme une collection d'objets Role en une collection d'objets RoleDTO.
     *
     * @param roleDTOCollection Une collection d'objets RoleDTO
     * @return une collection d'objet Role
     */
    public static Collection<Role> dtoToEntity(Collection<RoleDTO> roleDTOCollection) {
        if (roleDTOCollection == null) {
            return null;
        }
        List<Role> roleArrayList = new ArrayList<>();
        for (RoleDTO roleDTO : roleDTOCollection) {
            roleArrayList.add(dtoToEntity(roleDTO));
        }
        return roleArrayList;
    }

    /**
     * Transforme un objet RoleDTO en Role
     *
     * @param roleDTO Un objet roleDTO
     * @return une objet Role
     */
    public static Role dtoToEntity(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setNom(roleDTO.getNom());
        return role;
    }
}
