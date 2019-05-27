package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import com.projet.ecommerce.exception.InvalidDataException;
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
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ControllerUtilisateurTests {

	@Mock
	private UtilisateurBusiness utilisateurBusiness;

	@Mock
	private BindingResult bindingResult;

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
	public void addUser() throws InvalidDataException {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(2, "administrateur", 2, "admin@gmail.com", "brand", "arthur", "querty");
		when(bindingResult.hasErrors()).thenReturn(false);
		when(utilisateurBusiness.add(any(UtilisateurDTO.class))).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.createUser(utilisateurDTO, bindingResult);

		Assert.assertNotNull(retour);
		Assert.assertEquals(utilisateurDTO.getId(), retour.getId());
		Assert.assertEquals(utilisateurDTO.getNom(), retour.getNom());
		Assert.assertEquals(utilisateurDTO.getPrenom(), retour.getPrenom());
		Assert.assertEquals(utilisateurDTO.getEmail(), retour.getEmail());
		Assert.assertEquals(utilisateurDTO.getMdp(), retour.getMdp());
		Assert.assertEquals(utilisateurDTO.getRole().getId(), retour.getRole().getId());
		Assert.assertEquals(utilisateurDTO.getRole().getNom(), retour.getRole().getNom());
	}

	@Test(expected = InvalidDataException.class)
	public void addUserWithInvalidData() throws InvalidDataException {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(2, "administrateur", 2, "admin@gmail.com", "brand", "arthur", "");
		when(bindingResult.hasErrors()).thenReturn(true);

		controllerUtilisateur.createUser(utilisateurDTO, bindingResult);
	}

	@Test(expected = InvalidDataException.class)
	public void addUserWithBlankPassword() throws InvalidDataException {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(2, "administrateur", 2, "admin@gmail.com", "brand", "arthur", "\t");
		when(bindingResult.hasErrors()).thenReturn(false);

		controllerUtilisateur.createUser(utilisateurDTO, bindingResult);
	}

	@Test
	public void updateUser() throws InvalidDataException {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(2, "administrateur", 2, "admin@gmail.com", "brand", "arthur", "querty");
		when(bindingResult.hasErrors()).thenReturn(false);
		when(utilisateurBusiness.update(any(UtilisateurDTO.class))).thenReturn(utilisateurDTO);

		UtilisateurDTO retour = controllerUtilisateur.updateUser(utilisateurDTO, bindingResult);

		Assert.assertNotNull(retour);
		Assert.assertEquals(utilisateurDTO.getId(), retour.getId());
		Assert.assertEquals(utilisateurDTO.getNom(), retour.getNom());
		Assert.assertEquals(utilisateurDTO.getPrenom(), retour.getPrenom());
		Assert.assertEquals(utilisateurDTO.getEmail(), retour.getEmail());
		Assert.assertEquals(utilisateurDTO.getMdp(), retour.getMdp());
		Assert.assertEquals(utilisateurDTO.getRole().getId(), retour.getRole().getId());
		Assert.assertEquals(utilisateurDTO.getRole().getNom(), retour.getRole().getNom());
	}

	@Test(expected = InvalidDataException.class)
	public void updateUserWithInvalidData() throws InvalidDataException {

		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO(3, "administrateur", 3, "admin@gmail.com", "brand", "arthur", "querty");
		when(bindingResult.hasErrors()).thenReturn(true);

		controllerUtilisateur.updateUser(utilisateurDTO, bindingResult);
	}

}
