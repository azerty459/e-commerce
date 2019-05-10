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

import java.util.ArrayList;
import java.util.List;

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
	private UtilisateurDTO buildUtilisateurDTO() {
		RoleDTO role1 = new RoleDTO();
		role1.setId(1);
		role1.setNom("utilisateur");

		UtilisateurDTO firstDTO = new UtilisateurDTO();
		firstDTO.setId(1);
		firstDTO.setEmail("ludo@gmail.com");
		firstDTO.setNom("ludo");
		firstDTO.setPrenom("ludo");
		firstDTO.setMdp("azerty");
		firstDTO.setRole(role1);

		return firstDTO;
	}


	@Test
	public void getUserById() {

		List<UtilisateurDTO> utilisateursDTO = new ArrayList<>();
		utilisateursDTO.add(buildUtilisateurDTO());

		when(utilisateurBusiness.getUtilisateur(anyInt(), any(), any(), any(), any())).thenReturn(utilisateursDTO);

		List<UtilisateurDTO> listUtilisateursDTO = controllerUtilisateur.getUserById(1);

		Assert.assertNotNull(listUtilisateursDTO);
		Assert.assertEquals(1, listUtilisateursDTO.get(0).getId());
		Assert.assertEquals("ludo", listUtilisateursDTO.get(0).getNom());
	}

	@Test
	public void addUser() {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();

		when(utilisateurBusiness.add(any())).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.createUser(utilisateurDTO);

		Assert.assertNotNull(retour);
		Assert.assertEquals(1, retour.getId());
		Assert.assertEquals("ludo", retour.getNom());
	}

	@Test
	public void updateUser() {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();

		when(utilisateurBusiness.update(any())).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.updateUser(utilisateurDTO);

		Assert.assertNotNull(retour);
		Assert.assertEquals(1, retour.getId());
		Assert.assertEquals("ludo", retour.getNom());
	}

}
