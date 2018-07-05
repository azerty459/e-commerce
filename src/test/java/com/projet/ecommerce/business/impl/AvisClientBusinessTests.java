package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.repository.AvisClientRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AvisClientBusinessTests {

    @Mock
    private AvisClientRepository avisClientRepository;

    @InjectMocks
    private AvisClientBusiness avisClientBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void getAll() {
        List<AvisClient> listeAvisClients = new ArrayList<>();
        Mockito.when(avisClientRepository.findAll()).thenReturn(listeAvisClients);
        Assert.assertEquals(avisClientBusiness.getAll().size(), 0);

        AvisClient unAvisClient = new AvisClient();
        unAvisClient.setDescription("Bla bla bla");
        unAvisClient.setDateAvis(LocalDateTime.now());
        unAvisClient.setNote(2);

        listeAvisClients.add(unAvisClient);

        Mockito.when(avisClientRepository.findAll()).thenReturn(listeAvisClients);
        Mockito.verify(avisClientRepository, Mockito.times(1)).findAll();
        List<AvisClientDTO> avisClientsDTO = avisClientBusiness.getAll();
        Assert.assertEquals(avisClientsDTO.size(), 1);

        AvisClientDTO retour = avisClientsDTO.get(0);
        Assert.assertEquals(unAvisClient.getDescription(), retour.getDescription());
        Assert.assertEquals(unAvisClient.getDateAvis(), retour.getDate());
        Assert.assertEquals(unAvisClient.getNote(), retour.getNote());

        Mockito.verify(avisClientRepository, Mockito.times(2)).findAll();
    }

}
