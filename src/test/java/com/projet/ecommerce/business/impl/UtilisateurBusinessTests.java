package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.dto.transformer.UtilisateurTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Role;
import com.projet.ecommerce.persistance.entity.Utilisateur;
import com.projet.ecommerce.persistance.repository.RoleRepository;
import com.projet.ecommerce.persistance.repository.UtilisateurRepository;
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
	private Page page;

	@InjectMocks
	private UtilisateurBusiness utilisateurBusiness;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	//TODO Faire un test pour add

	@Test
	public void addNull() {
		Assert.assertNull(utilisateurBusiness.add(null));
	}

	@Test(expected = GraphQLCustomException.class)
	public void addWithEmailEmpty() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setEmail("");

		utilisateurBusiness.add(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void addWithMdpEmpty() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setMdp("");

		utilisateurBusiness.add(utilisateurDTO);
	}

	//TODO refaire le test il passe car il trouve un email (donc doublon et erreur) ne test absolument pas le role
	@Test
	public void addRoleNotExist() {
		//création de l'objet add utilisateur dto
		UtilisateurDTO addUtilisateurDTO = new UtilisateurDTO();
		addUtilisateurDTO.setPrenom("Toto");
		addUtilisateurDTO.setNom("Test");
		addUtilisateurDTO.setMdp("azerty");
		addUtilisateurDTO.setEmail("test@gmail.com");

		List<RoleDTO> roleDTOList = new ArrayList<>();
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setNom("Utilisateur");
		addUtilisateurDTO.setRole(roleDTO);

		//création de l'objet add utilisateur dto convertie en utilisateur
		Utilisateur utilisateurEntity = new Utilisateur();
		utilisateurEntity.setPrenom("Toto");
		utilisateurEntity.setNom("Test");
		utilisateurEntity.setMdp("azerty");
		utilisateurEntity.setEmail("test@gmail.com");
		Role role = new Role();
		role.setId(1);
		role.setNom("Utilisateur");
		utilisateurEntity.setRole(role);

		Mockito.when(utilisateurRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(utilisateurEntity));

		thrown.expect(GraphQLCustomException.class);
		utilisateurBusiness.add(addUtilisateurDTO);
	}

	@Test
	public void update() {
		String prenom = "John";
		String email = "john.shepard@normandy-sr2.space";
		Utilisateur retour = buildUtilisateur();
		Utilisateur nouveau = buildUtilisateur();
		nouveau.setPrenom(prenom);
		nouveau.setEmail(email);
		UtilisateurDTO param = UtilisateurTransformer.entityToDto(nouveau);

		Mockito.when(utilisateurRepository.findById(retour.getId())).thenReturn(Optional.of(retour));
		Mockito.when(roleRepository.findByNom(retour.getRole().getNom())).thenReturn(Optional.of(retour.getRole()));
		Mockito.when(passwordEncoder.encode(retour.getMdp())).thenReturn(retour.getMdp());
		Mockito.when(utilisateurRepository.save(Mockito.any(Utilisateur.class))).thenReturn(nouveau);

		//Test update normal
		UtilisateurDTO resultat = utilisateurBusiness.update(param);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(retour.getId(), resultat.getId());
		Assert.assertEquals(retour.getNom(), resultat.getNom());
		Assert.assertEquals(nouveau.getPrenom(), resultat.getPrenom());
		Assert.assertEquals(nouveau.getEmail(), resultat.getEmail());
		Assert.assertEquals(retour.getMdp(), resultat.getMdp());
		Assert.assertEquals(retour.getRole().getNom(), resultat.getRole().getNom());
		//Test update sans role
		retour.setRole(null);
		nouveau.setRole(null);
		param.setRole(null);
		resultat = utilisateurBusiness.update(param);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(retour.getId(), resultat.getId());
		Assert.assertEquals(retour.getNom(), resultat.getNom());
		Assert.assertEquals(nouveau.getPrenom(), resultat.getPrenom());
		Assert.assertEquals(nouveau.getEmail(), resultat.getEmail());
		Assert.assertEquals(retour.getMdp(), resultat.getMdp());
		Assert.assertNull(resultat.getRole());
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
	public void updateWithBlankEmail() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setEmail(" \t");

		utilisateurBusiness.update(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void updateWithoutPassword() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setMdp(null);

		utilisateurBusiness.update(utilisateurDTO);
	}

	@Test(expected = GraphQLCustomException.class)
	public void updateWithBlankPassword() {
		UtilisateurDTO utilisateurDTO = buildUtilisateurDTO();
		utilisateurDTO.setMdp(" \t");

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
		utilisateurDTO.setPrenom("Jane");
		utilisateurDTO.setNom("Shepard");
		utilisateurDTO.setMdp("Password");
		utilisateurDTO.setEmail("jane.shepard@normandy-sr2.earth");
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
