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

import static org.mockito.Mockito.*;

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
		RoleDTO role1 = new RoleDTO();
		role1.setId(idRole);
		role1.setNom(nomRole);

		UtilisateurDTO firstDTO = new UtilisateurDTO();
		firstDTO.setId(id);
		firstDTO.setEmail(email);
		firstDTO.setNom(nomUtilisateur);
		firstDTO.setPrenom(prenomUtilisateur);
		firstDTO.setMdp(mdp);
		firstDTO.setRole(role1);

		return firstDTO;
	}


	@Test
	public void getUserById() {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(1, "utilisateur", 1, "ludo@gmail.com", "lah", "ludo", "azerty");

		when(utilisateurBusiness.getUtilisateurById(anyInt())).thenReturn(utilisateurDTO);

		UtilisateurDTO UtilisateurDTO = controllerUtilisateur.getUser(1);

		Assert.assertNotNull(utilisateurDTO);
		Assert.assertEquals(1, utilisateurDTO.getId());
		Assert.assertEquals("lah", utilisateurDTO.getNom());
	}

	@Test
	public void addUser() {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(2, "administrateur", 2, "admin@gmail.com", "brand", "arthur", "querty");

		when(utilisateurBusiness.add(any())).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.createUser(utilisateurDTO);

		Assert.assertNotNull(retour);
		Assert.assertEquals(2, retour.getId());
		Assert.assertEquals("administrateur", retour.getRole().getNom());
	}

	@Test
	public void updateUser() {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(3, "administrateur", 3, "admin@gmail.com", "brand", "arthur", "querty");

		when(utilisateurBusiness.update(any())).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.updateUser(utilisateurDTO);

		Assert.assertNotNull(retour);
		Assert.assertEquals(3, retour.getId());
	}

}
