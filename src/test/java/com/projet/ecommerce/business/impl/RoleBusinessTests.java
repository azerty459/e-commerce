package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Role;
import com.projet.ecommerce.persistance.repository.RoleRepository;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RoleBusinessTests {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleBusiness roleBusiness;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRole_findByNom() {
        Role role = new Role();
        role.setId(1);
        role.setNom("Utilisateur");
        role.setUtilisateurs(new ArrayList<>());

        Mockito.when(roleRepository.findByNom(Mockito.anyString())).thenReturn(Optional.of(role));

        Collection<RoleDTO> roleCollection = roleBusiness.getRole(0, "Utilisateur");
        Assert.assertEquals(1, roleCollection.size());
    }

    @Test
    public void getRole_findByNomNotFound() {
        Role role = new Role();
        role.setId(1);
        role.setNom("Utilisateur");
        role.setUtilisateurs(new ArrayList<>());

        Mockito.when(roleRepository.findByNom(Mockito.anyString())).thenReturn(Optional.empty());

        thrown.expect(GraphQLCustomException.class);
        Collection<RoleDTO> roleCollection = roleBusiness.getRole(0, "Toto");
    }

    @Test
    public void getRole_findById() {
        Role role = new Role();
        role.setId(1);
        role.setNom("Utilisateur");
        role.setUtilisateurs(new ArrayList<>());

        Mockito.when(roleRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(role));

        Collection<RoleDTO> roleCollection = roleBusiness.getRole(1, null);
        Assert.assertEquals(1, roleCollection.size());
    }

    @Test
    public void getRole_findByIdNotFound() {
        Role role = new Role();
        role.setId(1);
        role.setNom("Utilisateur");
        role.setUtilisateurs(new ArrayList<>());

        Mockito.when(roleRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        thrown.expect(GraphQLCustomException.class);
        Collection<RoleDTO> roleCollection = roleBusiness.getRole(5, null);
    }


    @Test
    public void getRole_findByAll() {
        Role role = new Role();
        role.setId(1);
        role.setNom("Utilisateur");
        role.setUtilisateurs(new ArrayList<>());

        Collection<Role> roleCollection = new ArrayList<>();
        roleCollection.add(role);

        Mockito.when(roleRepository.findAllByOrderByNom()).thenReturn(roleCollection);

        Collection<RoleDTO> roleCollectionRetour = roleBusiness.getRole(0, null);
        Assert.assertEquals(1, roleCollectionRetour.size());
    }

    @Test
    public void add() {

    }

    @Test
    public void update() {

    }

    @Test
    public void delete() {

    }

    @Test
    public void getPage() {

    }
}
