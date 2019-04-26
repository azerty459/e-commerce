package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Role;
import com.projet.ecommerce.persistance.entity.Utilisateur;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UtilisateurRepositoryTests {

    private static final Utilisateur TEMP_INSERT = new Utilisateur();
    private static final Utilisateur TEMP_DELETE = new Utilisateur();
    private static final Utilisateur TEMP_UPDATE = new Utilisateur();
    private static final Utilisateur TEMP_GET = new Utilisateur();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT.setPrenom("Toto");
        TEMP_INSERT.setNom("Ruhade");
        TEMP_INSERT.setEmail("toto.ruhade@gmail.com");
        TEMP_INSERT.setMdp("azerty");

        TEMP_DELETE.setPrenom("Tata");
        TEMP_DELETE.setNom("Ruhade");
        TEMP_DELETE.setEmail("tata.ruhade@gmail.com");
        TEMP_DELETE.setMdp("azerty");

        TEMP_UPDATE.setPrenom("Titi");
        TEMP_UPDATE.setNom("Ruhade");
        TEMP_UPDATE.setEmail("titi.ruhade@gmail.com");
        TEMP_UPDATE.setMdp("azerty");
        TEMP_UPDATE.setRole(new Role());

        TEMP_GET.setPrenom("Tutu");
        TEMP_GET.setNom("Ruhade");
        TEMP_GET.setEmail("tutu.ruhade@gmail.com");
        TEMP_GET.setMdp("azerty");

        Role role = new Role();
        role.setNom("Utilisateur");

        TEMP_GET.setRole(role);
    }

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Before
    public void init() {
        utilisateurRepository.save(TEMP_GET);
    }

    @Test
    public void insert() {
        Utilisateur utilisateur = utilisateurRepository.save(TEMP_INSERT);
        Assert.assertNotNull(utilisateur);
        Assert.assertEquals(utilisateur.getMdp(), TEMP_INSERT.getMdp());
        Assert.assertEquals(utilisateur.getEmail(), TEMP_INSERT.getEmail());
        Assert.assertEquals(utilisateur.getPrenom(), TEMP_INSERT.getPrenom());
        Assert.assertEquals(utilisateur.getNom(), TEMP_INSERT.getNom());
    }

    @Test
    public void findByAll() {
        utilisateurRepository.deleteAll();
        Collection<Utilisateur> utilisateurCollection = utilisateurRepository.findAllByOrderByEmail();
        Assert.assertEquals(0, utilisateurCollection.size());

        utilisateurRepository.save(TEMP_INSERT);
        utilisateurCollection = utilisateurRepository.findAllByOrderByEmail();
        Assert.assertEquals(1, utilisateurCollection.size());
    }

    @Test
    public void findByEmail() {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(TEMP_GET.getEmail()).orElse(null);
        Assert.assertNotNull("L'objet utilisateur est null", utilisateur);

        Assert.assertEquals(utilisateur.getMdp(), TEMP_GET.getMdp());
        Assert.assertEquals(utilisateur.getEmail(), TEMP_GET.getEmail());
        Assert.assertEquals(utilisateur.getPrenom(), TEMP_GET.getPrenom());
        Assert.assertEquals(utilisateur.getNom(), TEMP_GET.getNom());
    }

    @Test
    public void findByEmailContainingIgnoreCaseOrderByEmail() {
        String email = TEMP_GET.getEmail().substring(2, 10).toUpperCase();
        Collection<Utilisateur> utilisateurCollection = utilisateurRepository.findByEmailContainingIgnoreCaseOrderByEmail(email);
        Assert.assertNotNull("La collection d'utilisateur est null", utilisateurCollection);

        Assert.assertEquals(1, utilisateurCollection.size());
    }

    @Test
    public void findByNomContainingIgnoreCaseOrderByNom() {
        String nom = TEMP_GET.getNom().substring(2, 5).toUpperCase();
        Collection<Utilisateur> utilisateurCollection = utilisateurRepository.findByNomContainingIgnoreCaseOrderByNom(nom);
        Assert.assertNotNull("La collection d'utilisateur est null", utilisateurCollection);

        Assert.assertEquals(1, utilisateurCollection.size());
    }

    @Test
    public void findByPrenomContainingIgnoreCaseOrderByPrenom() {
        String prenom = TEMP_GET.getPrenom().substring(1, 3).toUpperCase();
        Collection<Utilisateur> utilisateurCollection = utilisateurRepository.findByPrenomContainingIgnoreCaseOrderByPrenom(prenom);
        Assert.assertNotNull("La collection d'utilisateur est null", utilisateurCollection);

        Assert.assertEquals(1, utilisateurCollection.size());
    }

    @Test
    public void findByRoles_NomContainingIgnoreCase() {
        String nomRole = TEMP_GET.getRole().getNom().substring(2, 5).toUpperCase();
        Collection<Utilisateur> utilisateurCollection = utilisateurRepository.findByRole_NomContainingIgnoreCase(nomRole);
        Assert.assertEquals(1, utilisateurCollection.size());
    }

    @Test
    public void update() {
        // Initialisation
        Utilisateur utilisateur = utilisateurRepository.save(TEMP_UPDATE);

        Assert.assertEquals(utilisateur.getMdp(), TEMP_UPDATE.getMdp());
        Assert.assertEquals(utilisateur.getEmail(), TEMP_UPDATE.getEmail());
        Assert.assertEquals(utilisateur.getPrenom(), TEMP_UPDATE.getPrenom());
        Assert.assertEquals(utilisateur.getNom(), TEMP_UPDATE.getNom());

        // On modifie le rôle
        utilisateur.getRole().setNom("Fondateur");
        // On sauvegarde les modifications
        Utilisateur utilisateurRetour = utilisateurRepository.save(utilisateur);
        // On regarde si les modifications à étaient prises en compte
        Assert.assertEquals(utilisateurRetour.getMdp(), utilisateur.getMdp());
        Assert.assertEquals(utilisateurRetour.getEmail(), utilisateur.getEmail());
        Assert.assertEquals(utilisateurRetour.getPrenom(), utilisateur.getPrenom());
        Assert.assertEquals(utilisateurRetour.getNom(), utilisateur.getNom());
    }

    @Test
    public void delete() {
        Utilisateur utilisateur = utilisateurRepository.save(TEMP_DELETE);
        Assert.assertNotNull(utilisateur);
        utilisateurRepository.delete(TEMP_DELETE);
        Assert.assertFalse(utilisateurRepository.findById(utilisateur.getId()).isPresent());
    }

    @Test
    public void countUtilisateur() {
        long resultat = utilisateurRepository.countUtilisateurs();
        Assert.assertEquals(1, resultat);
        utilisateurRepository.save(TEMP_INSERT);
        resultat = utilisateurRepository.countUtilisateurs();
        Assert.assertEquals(2, resultat);
    }
}
