package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Role;
import com.projet.ecommerce.persistance.entity.Utilisateur;
import com.projet.ecommerce.persistance.repository.RoleRepository;
import com.projet.ecommerce.persistance.repository.UtilisateurRepository;
import com.projet.ecommerce.utilitaire.SendEmailUtilitaire;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UtilisateurBusinessTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private UtilisateurRepository utilisateurRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private SendEmailUtilitaire sendEmailUtilitaire;

	@Mock
	private Page page;

	@InjectMocks
	private UtilisateurBusiness utilisateurBusiness;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void add() {
		Utilisateur retour = buildUtilisateur();
		UtilisateurDTO param = buildUtilisateurDTO();
		Mockito.when(utilisateurRepository.findByEmail(param.getEmail())).thenReturn(Optional.empty());
		Mockito.when(roleRepository.findById(param.getRole().getId())).thenReturn(Optional.of(retour.getRole()));
		Mockito.when(passwordEncoder.encode(param.getMdp())).thenReturn(retour.getMdp());
		Mockito.when(utilisateurRepository.save(Mockito.any(Utilisateur.class))).thenReturn(retour);

		UtilisateurDTO resultat = utilisateurBusiness.add(param);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(retour.getId(), resultat.getId());
		Assert.assertEquals(retour.getEmail(), resultat.getEmail());
		Assert.assertEquals(retour.getNom(), resultat.getNom());
		Assert.assertEquals(retour.getPrenom(), resultat.getPrenom());
		Assert.assertEquals(retour.getMdp(), resultat.getMdp());
		Assert.assertEquals(retour.getRole().getId(), resultat.getRole().getId());
		Assert.assertEquals(retour.getRole().getNom(), resultat.getRole().getNom());
	}

	@Test
	public void addNull() {
		Assert.assertNull(utilisateurBusiness.add(null));
	}

	@Test(expected = GraphQLCustomException.class)
	public void addWithoutEmail() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setEmail(null);

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void addWithEmptyEmail() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setEmail("");

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void addWithBlankEmail() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setEmail(" \t");

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void addExistingEmail() {
		Utilisateur utilisateur = buildUtilisateur();
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		Mockito.when(utilisateurRepository.findByEmail(utilisateurDTO.getEmail())).thenReturn(Optional.of(utilisateur));

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void addWithoutPassword() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setMdp(null);

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void addWithEmptyPassword() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setMdp("");

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void addWithBlankPassword() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setMdp(" \t");

		utilisateurBusiness.add(utilisateurDTO);
	}


	@Test(expected = GraphQLCustomException.class)
	public void addWithoutRole() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setRole(null);
		Mockito.when(utilisateurRepository.findByEmail(utilisateurDTO.getEmail())).thenReturn(Optional.empty());

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void addRoleNotExist() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		Mockito.when(utilisateurRepository.findByEmail(utilisateurDTO.getEmail())).thenReturn(Optional.empty());
		Mockito.when(roleRepository.findById(utilisateurDTO.getId())).thenReturn(Optional.empty());

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test
	public void update() {
		String passwordHash = "jesuisunhashdemotdepasse";
		Utilisateur retour = buildUtilisateur();
		UtilisateurDTO param = buildUtilisateurDTO();

		Mockito.when(utilisateurRepository.findById(param.getId())).thenReturn(Optional.of(retour));
		Mockito.when(roleRepository.findById(param.getRole().getId())).thenReturn(Optional.of(retour.getRole()));
		Mockito.when(passwordEncoder.encode(param.getMdp())).thenReturn(passwordHash);
		Mockito.when(utilisateurRepository.save(Mockito.any(Utilisateur.class))).then(i -> i.getArgument(0));

		//Test update normal
		UtilisateurDTO resultat = utilisateurBusiness.update(param);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(retour.getId(), resultat.getId());
		Assert.assertEquals(param.getNom(), resultat.getNom());
		Assert.assertEquals(param.getPrenom(), resultat.getPrenom());
		Assert.assertEquals(param.getEmail(), resultat.getEmail());
		Assert.assertEquals(passwordHash, resultat.getMdp());
		Assert.assertEquals(param.getRole().getId(), resultat.getRole().getId());
		Assert.assertEquals(param.getRole().getNom(), resultat.getRole().getNom());
		//Test update sans role
		retour.setRole(null);
		param.setRole(null);
		resultat = utilisateurBusiness.update(param);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(retour.getId(), resultat.getId());
		Assert.assertEquals(param.getNom(), resultat.getNom());
		Assert.assertEquals(param.getPrenom(), resultat.getPrenom());
		Assert.assertEquals(param.getEmail(), resultat.getEmail());
		Assert.assertEquals(passwordHash, resultat.getMdp());
		Assert.assertNull(resultat.getRole());
		//Test sans mot de passe
		param.setMdp("");
		resultat = utilisateurBusiness.update(param);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(retour.getId(), resultat.getId());
		Assert.assertEquals(param.getNom(), resultat.getNom());
		Assert.assertEquals(param.getPrenom(), resultat.getPrenom());
		Assert.assertEquals(param.getEmail(), resultat.getEmail());
		Assert.assertEquals(retour.getMdp(), resultat.getMdp());
		Assert.assertNull(resultat.getRole());
		param.setMdp(null);
		resultat = utilisateurBusiness.update(param);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(retour.getMdp(), resultat.getMdp());
		param.setMdp("\t");
		resultat = utilisateurBusiness.update(param);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(retour.getMdp(), resultat.getMdp());
		//Test DTO Null
		resultat = utilisateurBusiness.update(null);
		Assert.assertNull(resultat);
	}

	@Test(expected = GraphQLCustomException.class)
	public void updateWithoutId() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setId(0);
		utilisateurBusiness.update(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void updateWithBadId() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setId(-8);
		utilisateurBusiness.update(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void updateWithIdNotFound() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setId(8);

		Mockito.when(utilisateurRepository.findById(utilisateurDTO.getId())).thenReturn(Optional.empty());

		utilisateurBusiness.update(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void updateWithoutEmail() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setEmail(null);

		utilisateurBusiness.update(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void updateWithEmptyEmail() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setEmail("");

		utilisateurBusiness.update(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void updateWithBlankEmail() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setEmail(" \t");

		utilisateurBusiness.update(utilisateurDTO);
	}

	@Test
	public void delete() {
		Assert.assertTrue(utilisateurBusiness.delete(null, 1));
		Assert.assertTrue(utilisateurBusiness.delete("totoàgmail.com", 0));
	}

	@Test
	public void getUtilisateur_findByEmailContainingIgnoreCaseOrderByEmail() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom("Titi");
		utilisateur.setNom("Ruhade");
		utilisateur.setEmail("titi.ruhade@gmail.com");
		utilisateur.setMdp("azerty");
		utilisateur.setRole(new Role());

		Collection<Utilisateur> utilisateurCollection = new ArrayList<>();
		utilisateurCollection.add(utilisateur);

		Mockito.when(utilisateurRepository.findByEmailContainingIgnoreCaseOrderByEmail(Mockito.anyString())).thenReturn(utilisateurCollection);

		Collection<UtilisateurDTO> roleCollectionRetour = utilisateurBusiness.getUtilisateur(0, "titi.ruhade@gmail.com", null, null, null);
		Assert.assertEquals(1, roleCollectionRetour.size());
	}

	@Test
	public void getUtilisateur_findByNomContainingIgnoreCaseOrderByNom() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom("Titi");
		utilisateur.setNom("Ruhade");
		utilisateur.setEmail("titi.ruhade@gmail.com");
		utilisateur.setMdp("azerty");
		utilisateur.setRole(new Role());

		Collection<Utilisateur> utilisateurCollection = new ArrayList<>();
		utilisateurCollection.add(utilisateur);

		Mockito.when(utilisateurRepository.findByNomContainingIgnoreCaseOrderByNom(Mockito.anyString())).thenReturn(utilisateurCollection);

		Collection<UtilisateurDTO> roleCollectionRetour = utilisateurBusiness.getUtilisateur(0, null, "Ruhade", null, null);
		Assert.assertEquals(1, roleCollectionRetour.size());
	}

	@Test
	public void getUtilisateur_findByPrenomContainingIgnoreCaseOrderByPrenom() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom("Titi");
		utilisateur.setNom("Ruhade");
		utilisateur.setEmail("titi.ruhade@gmail.com");
		utilisateur.setMdp("azerty");
		utilisateur.setRole(new Role());

		Collection<Utilisateur> utilisateurCollection = new ArrayList<>();
		utilisateurCollection.add(utilisateur);

		Mockito.when(utilisateurRepository.findByPrenomContainingIgnoreCaseOrderByPrenom(Mockito.anyString())).thenReturn(utilisateurCollection);

		Collection<UtilisateurDTO> roleCollectionRetour = utilisateurBusiness.getUtilisateur(0, null, null, "Titi", null);
		Assert.assertEquals(1, roleCollectionRetour.size());
	}

	@Test
	public void getUtilisateur_findByRoles_NomContainingIgnoreCase() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom("Titi");
		utilisateur.setNom("Ruhade");
		utilisateur.setEmail("titi.ruhade@gmail.com");
		utilisateur.setMdp("azerty");

		List<Role> roleList = new ArrayList<>();
		Role role = new Role();
		role.setId(1);
		role.setNom("Utilisateur");
		roleList.add(role);

		utilisateur.setRole(new Role());

		Collection<Utilisateur> utilisateurCollection = new ArrayList<>();
		utilisateurCollection.add(utilisateur);

		Mockito.when(utilisateurRepository.findByRole_NomContainingIgnoreCase(Mockito.anyString())).thenReturn(utilisateurCollection);

		Collection<UtilisateurDTO> roleCollectionRetour = utilisateurBusiness.getUtilisateur(0, null, null, null, "Utilisateur");
		Assert.assertEquals(1, roleCollectionRetour.size());
	}


	@Test
	public void getUtilisateur_findById() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom("Titi");
		utilisateur.setNom("Ruhade");
		utilisateur.setEmail("titi.ruhade@gmail.com");
		utilisateur.setMdp("azerty");
		utilisateur.setRole(new Role());

		Mockito.when(utilisateurRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(utilisateur));

		Collection<UtilisateurDTO> roleCollectionRetour = utilisateurBusiness.getUtilisateur(1, null, null, null, null);
		Assert.assertEquals(1, roleCollectionRetour.size());
	}

	@Test
	public void getUtilisateur_findByIDNotFound() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom("Titi");
		utilisateur.setNom("Ruhade");
		utilisateur.setEmail("titi.ruhade@gmail.com");
		utilisateur.setMdp("azerty");
		utilisateur.setRole(new Role());

		Mockito.when(utilisateurRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

		thrown.expect(GraphQLCustomException.class);
		Collection<UtilisateurDTO> roleCollection = utilisateurBusiness.getUtilisateur(99, null, null, null, null);
	}

	@Test
	public void getUtilisateur_findAllByOrderByEmail() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom("Titi");
		utilisateur.setNom("Ruhade");
		utilisateur.setEmail("titi.ruhade@gmail.com");
		utilisateur.setMdp("azerty");

		List<Role> roleList = new ArrayList<>();
		Role role = new Role();
		role.setId(1);
		role.setNom("Utilisateur");
		roleList.add(role);

		utilisateur.setRole(new Role());

		Collection<Utilisateur> utilisateurCollection = new ArrayList<>();
		utilisateurCollection.add(utilisateur);

		Mockito.when(utilisateurRepository.findAllByOrderByEmail()).thenReturn(utilisateurCollection);

		Collection<UtilisateurDTO> roleCollectionRetour = utilisateurBusiness.getUtilisateur(0, null, null, null, null);
		Assert.assertEquals(1, roleCollectionRetour.size());
	}


	@Test
	public void getPage() {
		Mockito.when(utilisateurRepository.findAllByOrderByEmail(Mockito.any(Pageable.class))).thenReturn(page);
		Assert.assertNotNull(utilisateurBusiness.getPage(1, 5));
	}

	@Test
	public void countUtilisateurs() {
		Long expected = 8L;
		Mockito.when(utilisateurRepository.countUtilisateurs()).thenReturn(expected);
		Assert.assertEquals(expected, utilisateurBusiness.countUtilisateurs());
	}

	@NotNull
	public Utilisateur buildUtilisateur() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(1);
		utilisateur.setPrenom("Jane");
		utilisateur.setNom("Shepard");
		utilisateur.setMdp("Password");
		utilisateur.setEmail("jane.shepard@normandy-sr2.earth");
		utilisateur.setRole(buildRole());
		return utilisateur;
	}

	@NotNull
	public UtilisateurDTO buildUtilisateurDTO() {
		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		utilisateurDTO.setId(1);
		utilisateurDTO.setPrenom("JaneDTO");
		utilisateurDTO.setNom("ShepardDTO");
		utilisateurDTO.setMdp("PasswordDTO");
		utilisateurDTO.setEmail("jane.shepard@normandy-sr2.earth.dto");
		utilisateurDTO.setRole(buildRoleDTO());
		return utilisateurDTO;
	}

	@NotNull
	public Role buildRole() {
		Role role = new Role();
		role.setId(1);
		role.setNom("Role test");
		return role;
	}

	@NotNull
	public RoleDTO buildRoleDTO() {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(1);
		roleDTO.setNom("Role test");
		return roleDTO;
	}

}
