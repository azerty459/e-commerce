package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ControllerUtilisateurTests {

	@Mock
	private UtilisateurBusiness utilisateurBusiness;

	@InjectMocks
	private ControllerUtilisateur controllerUtilisateur;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@NotNull
	private UtilisateurDTO buildUtilisateurDTO(int idRole, String nomRole, int id, String email, String nomUtilisateur, String prenomUtilisateur, String mdp) {
		RoleDTO role = new RoleDTO();
		role.setId(idRole);
		role.setNom(nomRole);

		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		utilisateurDTO.setId(id);
		utilisateurDTO.setEmail(email);
		utilisateurDTO.setNom(nomUtilisateur);
		utilisateurDTO.setPrenom(prenomUtilisateur);
		utilisateurDTO.setMdp(mdp);
		utilisateurDTO.setRole(role);

		return utilisateurDTO;
	}


	@Test
	public void getUserById() {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(1, "utilisateur", 1, "ludo@gmail.com", "lah", "ludo", "azerty");

		when(utilisateurBusiness.getUtilisateurById(utilisateurDTO.getId())).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.getUser(utilisateurDTO.getId());

		Assert.assertNotNull(retour);
		Assert.assertEquals(utilisateurDTO.getId(), retour.getId());
		Assert.assertEquals(utilisateurDTO.getNom(), retour.getNom());
		Assert.assertEquals(utilisateurDTO.getPrenom(), retour.getPrenom());
		Assert.assertEquals(utilisateurDTO.getEmail(), retour.getEmail());
		Assert.assertEquals(utilisateurDTO.getMdp(), retour.getMdp());
		Assert.assertEquals(utilisateurDTO.getRole().getId(), retour.getRole().getId());
		Assert.assertEquals(utilisateurDTO.getRole().getNom(), retour.getRole().getNom());
	}

	@Test
	public void addUser() {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(2, "administrateur", 2, "admin@gmail.com", "brand", "arthur", "querty");

		when(utilisateurBusiness.add(any(UtilisateurDTO.class))).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.createUser(utilisateurDTO);

		Assert.assertNotNull(retour);
		Assert.assertEquals(utilisateurDTO.getId(), retour.getId());
		Assert.assertEquals(utilisateurDTO.getNom(), retour.getNom());
		Assert.assertEquals(utilisateurDTO.getPrenom(), retour.getPrenom());
		Assert.assertEquals(utilisateurDTO.getEmail(), retour.getEmail());
		Assert.assertEquals(utilisateurDTO.getMdp(), retour.getMdp());
		Assert.assertEquals(utilisateurDTO.getRole().getId(), retour.getRole().getId());
		Assert.assertEquals(utilisateurDTO.getRole().getNom(), retour.getRole().getNom());
	}

	@Test
	public void updateUser() {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(3, "administrateur", 3, "admin@gmail.com", "brand", "arthur", "querty");

		when(utilisateurBusiness.update(any(UtilisateurDTO.class))).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.updateUser(utilisateurDTO);

		Assert.assertNotNull(retour);
		Assert.assertEquals(utilisateurDTO.getId(), retour.getId());
		Assert.assertEquals(utilisateurDTO.getNom(), retour.getNom());
		Assert.assertEquals(utilisateurDTO.getPrenom(), retour.getPrenom());
		Assert.assertEquals(utilisateurDTO.getEmail(), retour.getEmail());
		Assert.assertEquals(utilisateurDTO.getMdp(), retour.getMdp());
		Assert.assertEquals(utilisateurDTO.getRole().getId(), retour.getRole().getId());
		Assert.assertEquals(utilisateurDTO.getRole().getNom(), retour.getRole().getNom());
	}

}
