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

    private static final Utilisateur UTILISATEUR;

    private static final UtilisateurDTO UTILISATEUR_DTO;

    private static final Role ROLE;

    private static final RoleDTO ROLE_DTO;

    static {
        ROLE = new Role();
        ROLE.setId(1);
        ROLE.setNom("Utilisateur");

        ROLE_DTO = new RoleDTO();
        ROLE_DTO.setId(1);
        ROLE_DTO.setNom("Utilisateur");

        UTILISATEUR = new Utilisateur();
        UTILISATEUR.setId(1);
        UTILISATEUR.setEmail("test@gmail.com");
        UTILISATEUR.setMdp("test");
        UTILISATEUR.setPrenom("Toto");
        UTILISATEUR.setNom("Test");

        ArrayList<Role> roleArrayList = new ArrayList<>();
        roleArrayList.add(ROLE);
        UTILISATEUR.setRoles(roleArrayList);

        ArrayList<Utilisateur> utilisateurArrayList = new ArrayList<>();
        utilisateurArrayList.add(UTILISATEUR);
        ROLE.setUtilisateurs(utilisateurArrayList);

        UTILISATEUR_DTO = new UtilisateurDTO();
        UTILISATEUR_DTO.setId(1);
        UTILISATEUR_DTO.setEmail("test@gmail.com");
        UTILISATEUR_DTO.setMdp("test");
        UTILISATEUR_DTO.setPrenom("Toto");
        UTILISATEUR_DTO.setNom("Test");

        ArrayList<RoleDTO> roleDTOArrayList = new ArrayList<>();
        roleDTOArrayList.add(ROLE_DTO);
        UTILISATEUR_DTO.setRoles(roleDTOArrayList);
    }

    @Test
    public void singleDtoToEntity() {
        Utilisateur utilisateur = UtilisateurTransformer.dtoToEntity(UTILISATEUR_DTO);
        Assert.assertNotNull(utilisateur);

        Assert.assertEquals(utilisateur.getMdp(), UTILISATEUR_DTO.getMdp());
        Assert.assertEquals(utilisateur.getEmail(), UTILISATEUR_DTO.getEmail());
        Assert.assertEquals(utilisateur.getId(), UTILISATEUR_DTO.getId());
        Assert.assertEquals(utilisateur.getNom(), UTILISATEUR_DTO.getNom());
        Assert.assertEquals(utilisateur.getPrenom(), UTILISATEUR_DTO.getPrenom());
        Assert.assertEquals(utilisateur.getRoles().size(), 1);

        List<Role> roleList = new ArrayList<>(utilisateur.getRoles());
        List<RoleDTO> roleDTOList = new ArrayList<>(UTILISATEUR_DTO.getRoles());
        Assert.assertEquals(roleList.get(0).getId(), roleDTOList.get(0).getId());
        Assert.assertEquals(roleList.get(0).getNom(), roleDTOList.get(0).getNom());
    }

    @Test
    public void singleEntityToDto() {
        UtilisateurDTO utilisateurDTO = UtilisateurTransformer.entityToDto(UTILISATEUR);
        Assert.assertNotNull(utilisateurDTO);

        Assert.assertEquals(UTILISATEUR.getMdp(), utilisateurDTO.getMdp());
        Assert.assertEquals(UTILISATEUR.getEmail(), utilisateurDTO.getEmail());
        Assert.assertEquals(UTILISATEUR.getId(), utilisateurDTO.getId());
        Assert.assertEquals(UTILISATEUR.getNom(), utilisateurDTO.getNom());
        Assert.assertEquals(UTILISATEUR.getPrenom(), utilisateurDTO.getPrenom());
        Assert.assertEquals(1, utilisateurDTO.getRoles().size());

        List<RoleDTO> roleDTOList = new ArrayList<>(utilisateurDTO.getRoles());
        List<Role> roleList = new ArrayList<>(UTILISATEUR.getRoles());
        Assert.assertEquals(roleDTOList.get(0).getId(), roleList.get(0).getId());
        Assert.assertEquals(roleDTOList.get(0).getNom(), roleList.get(0).getNom());
    }

    @Test
    public void severalDtoToEntity() {
        UtilisateurDTO utilisateurDTO2 = new UtilisateurDTO();
        utilisateurDTO2.setId(2);
        utilisateurDTO2.setEmail("test2@gmail.com");
        utilisateurDTO2.setMdp("test2");
        utilisateurDTO2.setPrenom("Toto2");
        utilisateurDTO2.setNom("Test2");

        Collection<UtilisateurDTO> utilisateurDTOList = new ArrayList<>();
        utilisateurDTOList.add(UTILISATEUR_DTO);
        utilisateurDTOList.add(utilisateurDTO2);

        List<Utilisateur> utilisateurList = new ArrayList<>(UtilisateurTransformer.dtoToEntity(utilisateurDTOList));

        Assert.assertNotNull(utilisateurList);

        Assert.assertEquals(utilisateurList.get(0).getMdp(), UTILISATEUR_DTO.getMdp());
        Assert.assertEquals(utilisateurList.get(0).getEmail(), UTILISATEUR_DTO.getEmail());
        Assert.assertEquals(utilisateurList.get(0).getId(), UTILISATEUR_DTO.getId());
        Assert.assertEquals(utilisateurList.get(0).getNom(), UTILISATEUR_DTO.getNom());
        Assert.assertEquals(utilisateurList.get(0).getPrenom(), UTILISATEUR_DTO.getPrenom());
        Assert.assertEquals(1, utilisateurList.get(0).getRoles().size());


        Assert.assertEquals(utilisateurList.get(1).getMdp(), utilisateurDTO2.getMdp());
        Assert.assertEquals(utilisateurList.get(1).getEmail(), utilisateurDTO2.getEmail());
        Assert.assertEquals(utilisateurList.get(1).getId(), utilisateurDTO2.getId());
        Assert.assertEquals(utilisateurList.get(1).getNom(), utilisateurDTO2.getNom());
        Assert.assertEquals(utilisateurList.get(1).getPrenom(), utilisateurDTO2.getPrenom());
        Assert.assertEquals(0, utilisateurList.get(1).getRoles().size());
    }

    @Test
    public void severalEntityToDto() {
        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setId(2);
        utilisateur2.setEmail("test2@gmail.com");
        utilisateur2.setMdp("test2");
        utilisateur2.setPrenom("Toto2");
        utilisateur2.setNom("Test2");
        utilisateur2.setRoles(new ArrayList<>());

        Collection<Utilisateur> utilisateurArrayList = new ArrayList<>();
        utilisateurArrayList.add(UTILISATEUR);
        utilisateurArrayList.add(utilisateur2);

        List<UtilisateurDTO> utilisateurDTOList = new ArrayList<>(UtilisateurTransformer.entityToDto(utilisateurArrayList));

        Assert.assertNotNull(utilisateurDTOList);

        Assert.assertEquals(utilisateurDTOList.get(0).getMdp(), UTILISATEUR.getMdp());
        Assert.assertEquals(utilisateurDTOList.get(0).getEmail(), UTILISATEUR.getEmail());
        Assert.assertEquals(utilisateurDTOList.get(0).getId(), UTILISATEUR.getId());
        Assert.assertEquals(utilisateurDTOList.get(0).getNom(), UTILISATEUR.getNom());
        Assert.assertEquals(utilisateurDTOList.get(0).getPrenom(), UTILISATEUR.getPrenom());
        Assert.assertEquals(1, utilisateurDTOList.get(0).getRoles().size());


        Assert.assertEquals(utilisateurDTOList.get(1).getMdp(), utilisateur2.getMdp());
        Assert.assertEquals(utilisateurDTOList.get(1).getEmail(), utilisateur2.getEmail());
        Assert.assertEquals(utilisateurDTOList.get(1).getId(), utilisateur2.getId());
        Assert.assertEquals(utilisateurDTOList.get(1).getNom(), utilisateur2.getNom());
        Assert.assertEquals(utilisateurDTOList.get(1).getPrenom(), utilisateur2.getPrenom());
        Assert.assertEquals(0, utilisateurDTOList.get(1).getRoles().size());
    }
}