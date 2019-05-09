package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RoleRepositoryTests {

	private static final Role TEMP_INSERT = new Role();
	private static final Role TEMP_DELETE = new Role();
	private static final Role TEMP_UPDATE = new Role();
	private static final Role TEMP_GET = new Role();

	static {
		//Permet d'écraser la config application.properties par application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");

		TEMP_INSERT.setNom("Utilisateur");
		TEMP_INSERT.setUtilisateurs(new ArrayList<>());

		TEMP_DELETE.setNom("Gestionnaire");
		TEMP_DELETE.setUtilisateurs(new ArrayList<>());

		TEMP_UPDATE.setNom("Administrateur");
		TEMP_UPDATE.setUtilisateurs(new ArrayList<>());

		TEMP_GET.setNom("Visiteur");
		TEMP_GET.setUtilisateurs(new ArrayList<>());
	}

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void insertProduit() {
		Role role = roleRepository.save(TEMP_INSERT);
		Assert.assertNotNull(role);
		Assert.assertEquals(role.getNom(), TEMP_INSERT.getNom());
		Assert.assertEquals(role.getUtilisateurs().size(), TEMP_INSERT.getUtilisateurs().size());
	}

	@Test
	public void findAllByOrderByNom() {
		roleRepository.deleteAll();
		Collection<Role> roleCollection = roleRepository.findAllByOrderByNom();
		Assert.assertEquals(0, roleCollection.size());

		roleRepository.save(TEMP_INSERT);
		roleCollection = roleRepository.findAllByOrderByNom();
		Assert.assertEquals(1, roleCollection.size());
	}

	@Test
	public void findByID() {
		Assert.assertNotNull(roleRepository.save(TEMP_GET));
		Role role = roleRepository.findById(TEMP_GET.getId()).orElse(null);
		Assert.assertNotNull("L'objet role est null", role);
		Assert.assertEquals(role.getNom(), TEMP_GET.getNom());
		Assert.assertEquals(role.getUtilisateurs().size(), TEMP_GET.getUtilisateurs().size());
	}

	@Test
	public void findByNom() {
		Assert.assertNotNull(roleRepository.save(TEMP_GET));
		Role role = roleRepository.findByNom(TEMP_GET.getNom()).orElse(null);
		Assert.assertNotNull("L'objet role est null", role);
		Assert.assertEquals(role.getNom(), TEMP_GET.getNom());
		Assert.assertEquals(role.getUtilisateurs().size(), TEMP_GET.getUtilisateurs().size());
	}

	@Test
	public void update() {
		Role role = roleRepository.save(TEMP_UPDATE);
		Assert.assertEquals(role.getNom(), TEMP_UPDATE.getNom());
		Assert.assertEquals(role.getUtilisateurs().size(), TEMP_UPDATE.getUtilisateurs().size());

		// On modifie le rôle
		role.setNom("Fondateur");
		// On sauvegarde les modifications
		Role retourRole = roleRepository.save(role);
		// On regarde si les modifications à étaient prises en compte
		Assert.assertEquals(retourRole.getNom(), "Fondateur");
		Assert.assertEquals(retourRole.getId(), role.getId());
		Assert.assertEquals(retourRole.getUtilisateurs().size(), role.getUtilisateurs().size());
	}

	@Test
	public void delete() {
		Role role = roleRepository.save(TEMP_DELETE);
		Assert.assertNotNull(role);
		roleRepository.delete(TEMP_DELETE);
		Assert.assertFalse(roleRepository.findById(role.getId()).isPresent());
	}

}
