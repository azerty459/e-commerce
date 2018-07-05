package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.AvisClientRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class AvisClientBusinessTests {

    @Mock
    private AvisClientRepository avisClientRepository;

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private AvisClientBusiness avisClientBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void can_getAll_with_RefProduit(){
        List<AvisClient> listeAvisClients = this.getListAvisClientTest();

        Mockito.when(avisClientRepository.findByProduit_ReferenceProduit("A05A02"))
                .thenReturn(listeAvisClients);

        List<AvisClientDTO> avisClientsDTO = avisClientBusiness.getAll("A05A02");

        Mockito.verify(avisClientRepository, Mockito.times(1))
                .findByProduit_ReferenceProduit("A05A02");

        this.assertData(listeAvisClients.get(0), avisClientsDTO.get(0));

    }

    private void assertData(AvisClient unAvisClient, AvisClientDTO avisClientDTO){
        Assert.assertNotNull(unAvisClient);
        Assert.assertNotNull(avisClientDTO);
        Assert.assertEquals(unAvisClient.getDescription(), avisClientDTO.getDescription());
        Assert.assertEquals(unAvisClient.getDate(), avisClientDTO.getDate());
        Assert.assertEquals(unAvisClient.getNote(), avisClientDTO.getNote());
        Assert.assertEquals(unAvisClient.getId(), avisClientDTO.getId());
    }

    private void assertData(AvisClientDTO avisClientDTO1, AvisClientDTO avisClientDTO2){
        Assert.assertNotNull(avisClientDTO1);
        Assert.assertNotNull(avisClientDTO2);
        Assert.assertEquals(avisClientDTO1.getDescription(), avisClientDTO2.getDescription());
        Assert.assertEquals(avisClientDTO1.getDate(), avisClientDTO2.getDate());
        Assert.assertEquals(avisClientDTO1.getNote(), avisClientDTO2.getNote());
        Assert.assertEquals(avisClientDTO1.getId(), avisClientDTO2.getId());
    }

    @Test
    public void can_getAll_without_RefProduit(){
        List<AvisClient> listeAvisClients = this.getListAvisClientTest();

        Mockito.when(avisClientRepository.findAll()).thenReturn(listeAvisClients);
        List<AvisClientDTO> avisClientsDTO = avisClientBusiness.getAll();
        Assert.assertEquals(avisClientsDTO.size(), 1);

        this.assertData(listeAvisClients.get(0), avisClientsDTO.get(0));
        Mockito.verify(avisClientRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void can_getAll_no_data() {
        Mockito.when(avisClientRepository.findAll()).thenReturn(new ArrayList<>());
        List<AvisClientDTO> avisClient = avisClientBusiness.getAll();
        Assert.assertNotNull(avisClient);
        Assert.assertEquals(avisClient.size(), 0);
        Mockito.verify(avisClientRepository, Mockito.times(1)).findAll();
    }

    @Test(expected = GraphQLCustomException.class)
    public void can_t_update_avis_client_not_found() {
        AvisClientDTO unAvisClient = new AvisClientDTO();
        unAvisClient.setId(1);
        Mockito.when(avisClientRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        avisClientBusiness.update(unAvisClient);
    }

    @Test
    public void cant_update_avis_client_null() {
        AvisClientDTO avisClientDTO = avisClientBusiness.update(null);
        Assert.assertNull(avisClientDTO);
    }

    @Test
    public void cant_delete_avis_client_null() {
        boolean deleteOK = avisClientBusiness.delete(null);
        Assert.assertFalse(deleteOK);
    }

    @Test(expected = GraphQLCustomException.class)
    public void cant_delete_avis_client_not_found() {
        AvisClient avisClientToDelete = new AvisClient();
        avisClientToDelete.setId(1);
        Mockito.when(avisClientRepository.findById(avisClientToDelete.getId())).thenReturn(Optional.empty());
        avisClientBusiness.delete(avisClientToDelete.getId());
    }

    @Test
    public void can_delete_avis_client() {
        AvisClient avisClientToDelete = new AvisClient();
        avisClientToDelete.setId(1);
        Mockito.when(avisClientRepository.findById(avisClientToDelete.getId())).thenReturn(Optional.of(avisClientToDelete));
        avisClientBusiness.delete(avisClientToDelete.getId());
        Mockito.verify(avisClientRepository, Mockito.times(1)).deleteById(avisClientToDelete.getId());
    }

    @Test
    public void can_update_avis_client() {
        AvisClient oldAvisClient = new AvisClient();
        oldAvisClient.setId(1);
        oldAvisClient.setDescription("Bla bla bla");
        oldAvisClient.setNote(3);

        AvisClientDTO newAvisClient = new AvisClientDTO();
        newAvisClient.setId(oldAvisClient.getId());
        newAvisClient.setDescription("Modification");
        newAvisClient.setNote(4);

        Mockito.when(avisClientRepository.findById(newAvisClient.getId())).thenReturn(Optional.of(oldAvisClient));
        Mockito.when(avisClientRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

        AvisClientDTO avisClientModified = avisClientBusiness.update(newAvisClient);
        this.assertData(newAvisClient, avisClientModified);
    }


    @Test(expected = GraphQLCustomException.class)
    public void can_add_avis_client_product_not_found() {
        AvisClientDTO avis = this.getClientToAdd();

        Mockito.when(avisClientRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(produitRepository.findById(avis.getRefProduit())).thenReturn(Optional.empty());
        avisClientBusiness.add(avis);
    }

    @Test
    public void can_add_avis_client() {
        AvisClientDTO avisClientToAdd = this.getClientToAdd();

        Produit p = new Produit();
        p.setReferenceProduit(avisClientToAdd.getRefProduit());

        Mockito.when(avisClientRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(produitRepository.findById(avisClientToAdd.getRefProduit())).thenReturn(Optional.of(p));
        AvisClientDTO newAvisClient = avisClientBusiness.add(avisClientToAdd);

        this.assertData(avisClientToAdd, newAvisClient);
    }

    @Test(expected = GraphQLCustomException.class)
    public void cant_add_avis_client_without_description() {
        //On ne teste pas les "null" car dans le fonctionnel GraphQL ne laisse pas passer les null
        AvisClientDTO avis = this.getClientToAdd();
        avis.setDescription(StringUtils.EMPTY);
        avisClientBusiness.add(avis);

    }

    @Test(expected = GraphQLCustomException.class)
    public void cant_add_avis_client_without_note() {
        //On ne teste pas les "null" car dans le fonctionnel GraphQL ne laisse pas passer les null
        AvisClientDTO avis = this.getClientToAdd();
        avis.setNote(0);
        avisClientBusiness.add(avis);
    }

    @Test(expected = GraphQLCustomException.class)
    public void cant_add_avis_client_without_refProduit() {
        //On ne teste pas les "null" car dans le fonctionnel GraphQL ne laisse pas passer les null
        AvisClientDTO avis = this.getClientToAdd();
        avis.setRefProduit(StringUtils.EMPTY);
        avisClientBusiness.add(avis);
    }

    private AvisClientDTO getClientToAdd(){
        AvisClientDTO avis = new AvisClientDTO();
        avis.setDescription("A ajouter");
        avis.setNote(5);
        avis.setRefProduit("AAA");
        return avis;
    }

    private List<AvisClient> getListAvisClientTest(){
        List<AvisClient> listeAvisClients = new ArrayList<>();
        AvisClient unAvisClient = new AvisClient();
        unAvisClient.setDescription("Bla bla bla");
        unAvisClient.setDate(LocalDateTime.now());
        unAvisClient.setNote(2);
        listeAvisClients.add(unAvisClient);
        return listeAvisClients;
    }

}
