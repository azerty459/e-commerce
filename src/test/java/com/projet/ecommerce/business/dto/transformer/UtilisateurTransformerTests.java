package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.persistance.entity.Role;
import com.projet.ecommerce.persistance.entity.Utilisateur;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
public class UtilisateurTransformerTests {

    @Test
    public void singleDtoToEntity() {
        UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(1);
        Utilisateur utilisateur = UtilisateurTransformer.dtoToEntity(utilisateurDTO);
        Assert.assertNotNull(utilisateur);

        Assert.assertEquals(utilisateur.getMdp(), utilisateurDTO.getMdp());
        Assert.assertEquals(utilisateur.getEmail(), utilisateurDTO.getEmail());
        Assert.assertEquals(utilisateur.getId(), utilisateurDTO.getId());
        Assert.assertEquals(utilisateur.getNom(), utilisateurDTO.getNom());
        Assert.assertEquals(utilisateur.getPrenom(), utilisateurDTO.getPrenom());
        Assert.assertEquals(utilisateur.getRole().getId(), utilisateurDTO.getRole().getId());

        Role role = utilisateur.getRole();
        RoleDTO roleDTO = buildRoleDto(utilisateurDTO);
        Assert.assertEquals(role.getId(), roleDTO.getId());
        Assert.assertEquals(role.getNom(), roleDTO.getNom());
    }

    @Test
    public void singleEntityToDto() {
        Utilisateur utilisateur = buildUtilisateur(1);
        UtilisateurDTO utilisateurDTO = UtilisateurTransformer.entityToDto(utilisateur);
        Assert.assertNotNull(utilisateurDTO);

        Assert.assertEquals(utilisateur.getMdp(), utilisateurDTO.getMdp());
        Assert.assertEquals(utilisateur.getEmail(), utilisateurDTO.getEmail());
        Assert.assertEquals(utilisateur.getId(), utilisateurDTO.getId());
        Assert.assertEquals(utilisateur.getNom(), utilisateurDTO.getNom());
        Assert.assertEquals(utilisateur.getPrenom(), utilisateurDTO.getPrenom());
        Assert.assertEquals(utilisateur.getRole().getId(), utilisateurDTO.getRole().getId());

        RoleDTO roleDTO = utilisateurDTO.getRole();
        Role role = buildRole(utilisateur);
        Assert.assertEquals(roleDTO.getId(), role.getId());
        Assert.assertEquals(roleDTO.getNom(), role.getNom());
    }

    @Test
    public void severalDtoToEntity() {
        UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(1);
        UtilisateurDTO utilisateurDTO2 = buildUtilisateurDTO(2);

        List<UtilisateurDTO> utilisateurDTOList = new ArrayList<>();
        utilisateurDTOList.add(utilisateurDTO);
        utilisateurDTOList.add(utilisateurDTO2);

        List<Utilisateur> utilisateurList = new ArrayList<>(UtilisateurTransformer.dtoToEntity(utilisateurDTOList));

        Assert.assertNotNull(utilisateurList);

        Assert.assertEquals(utilisateurList.get(0).getMdp(), utilisateurDTO.getMdp());
        Assert.assertEquals(utilisateurList.get(0).getEmail(), utilisateurDTO.getEmail());
        Assert.assertEquals(utilisateurList.get(0).getId(), utilisateurDTO.getId());
        Assert.assertEquals(utilisateurList.get(0).getNom(), utilisateurDTO.getNom());
        Assert.assertEquals(utilisateurList.get(0).getPrenom(), utilisateurDTO.getPrenom());


        Assert.assertEquals(utilisateurList.get(1).getMdp(), utilisateurDTO2.getMdp());
        Assert.assertEquals(utilisateurList.get(1).getEmail(), utilisateurDTO2.getEmail());
        Assert.assertEquals(utilisateurList.get(1).getId(), utilisateurDTO2.getId());
        Assert.assertEquals(utilisateurList.get(1).getNom(), utilisateurDTO2.getNom());
        Assert.assertEquals(utilisateurList.get(1).getPrenom(), utilisateurDTO2.getPrenom());
    }

    @Test
    public void severalEntityToDto() {
        Utilisateur utilisateur = buildUtilisateur(1);
        Utilisateur utilisateur2 = buildUtilisateur(2);

        List<Utilisateur> utilisateurArrayList = new ArrayList<>();
        utilisateurArrayList.add(utilisateur);
        utilisateurArrayList.add(utilisateur2);

        List<UtilisateurDTO> utilisateurDTOList = new ArrayList<>(UtilisateurTransformer.entityToDto(utilisateurArrayList));

        Assert.assertNotNull(utilisateurDTOList);

        Assert.assertEquals(utilisateurDTOList.get(0).getMdp(), utilisateur.getMdp());
        Assert.assertEquals(utilisateurDTOList.get(0).getEmail(), utilisateur.getEmail());
        Assert.assertEquals(utilisateurDTOList.get(0).getId(), utilisateur.getId());
        Assert.assertEquals(utilisateurDTOList.get(0).getNom(), utilisateur.getNom());
        Assert.assertEquals(utilisateurDTOList.get(0).getPrenom(), utilisateur.getPrenom());


        Assert.assertEquals(utilisateurDTOList.get(1).getMdp(), utilisateur2.getMdp());
        Assert.assertEquals(utilisateurDTOList.get(1).getEmail(), utilisateur2.getEmail());
        Assert.assertEquals(utilisateurDTOList.get(1).getId(), utilisateur2.getId());
        Assert.assertEquals(utilisateurDTOList.get(1).getNom(), utilisateur2.getNom());
        Assert.assertEquals(utilisateurDTOList.get(1).getPrenom(), utilisateur2.getPrenom());
    }

    //Builders

    public Role buildRole(Utilisateur utilisateur) {
        Role role = new Role();
        int id = utilisateur.getId();
        role.setId(id);
        role.setNom("Role_name "+id);
        role.setUtilisateurs(new ArrayList<>());
        List<Utilisateur> utilisateurList = new ArrayList<>();
        utilisateurList.add(utilisateur);
        role.setUtilisateurs(utilisateurList);
        return role;
    }

    public RoleDTO buildRoleDto(UtilisateurDTO utilisateurDTO) {
        RoleDTO roleDTO = new RoleDTO();
        int id = utilisateurDTO.getId();
        roleDTO.setNom("Role_name "+id);
        return roleDTO;
    }

    public Utilisateur buildUtilisateur(int id) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);
        utilisateur.setNom("User_name "+id);
        utilisateur.setPrenom("User_lastName "+id);
        utilisateur.setEmail("User_email "+id);
        utilisateur.setMdp("User_secret "+id);
        utilisateur.setRole(buildRole(utilisateur));
        return utilisateur;
    }

    public UtilisateurDTO buildUtilisateurDTO(int id) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setId(id);
        utilisateurDTO.setNom("User_name "+id);
        utilisateurDTO.setPrenom("User_lastName "+id);
        utilisateurDTO.setEmail("User_email "+id);
        utilisateurDTO.setMdp("User_secret "+id);
        utilisateurDTO.setRole(buildRoleDto(utilisateurDTO));
        return utilisateurDTO;
    }
}