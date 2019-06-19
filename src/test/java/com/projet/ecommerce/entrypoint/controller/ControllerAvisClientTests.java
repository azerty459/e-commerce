package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.business.impl.AvisClientBusiness;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ControllerAvisClientTests {

	@Mock
	private AvisClientBusiness avisClientBusiness;

	@InjectMocks
	private ControllerAvisClient controllerAvisClient;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@NotNull
	private AvisClientDTO buildAvisClientDTO(int id, String description, Integer note, String ref) {
		AvisClientDTO avisClientDTO = new AvisClientDTO();
		avisClientDTO.setId(id);
		avisClientDTO.setDescription(description);
		avisClientDTO.setNote(note);
		avisClientDTO.setRefProduit(ref);
		avisClientDTO.setDate(null);

		return avisClientDTO;
	}

	@Test
	public void getAll() {
		AvisClientDTO avisClientDTO = buildAvisClientDTO(1, "bien", 3, "A05A05");
		Collection<AvisClientDTO> avisClientDTOCollection = new ArrayList<>();
		avisClientDTOCollection.add(avisClientDTO);

		when(avisClientBusiness.getAll()).thenReturn(avisClientDTOCollection);

		Collection<AvisClientDTO> retour = controllerAvisClient.getAll();
		List<AvisClientDTO> avisClientDTOList = new ArrayList<>();

		retour.stream().forEach(elt -> avisClientDTOList.add(elt));

		Assert.assertEquals(avisClientDTOList.get(0).getId(), avisClientDTO.getId());
		Assert.assertEquals(avisClientDTOList.get(0).getDescription(), avisClientDTO.getDescription());
		Assert.assertEquals(avisClientDTOList.get(0).getNote(), avisClientDTO.getNote());
		Assert.assertEquals(avisClientDTOList.get(0).getRefProduit(), avisClientDTO.getRefProduit());
	}

	@Test
	public void getAllByRefProduct() {
		AvisClientDTO avisClientDTO = buildAvisClientDTO(2, "bof", 2, "A06A06");
		Collection<AvisClientDTO> avisClientDTOCollection = new ArrayList<>();
		avisClientDTOCollection.add(avisClientDTO);

		when(avisClientBusiness.getAll(Mockito.anyString())).thenReturn(avisClientDTOCollection);

		Collection<AvisClientDTO> retour = controllerAvisClient.getAllByRef(avisClientDTO.getRefProduit());
		List<AvisClientDTO> avisClientDTOList = new ArrayList<>();

		retour.stream().forEach(elt -> avisClientDTOList.add(elt));

		Assert.assertEquals(avisClientDTOList.get(0).getId(), avisClientDTO.getId());
		Assert.assertEquals(avisClientDTOList.get(0).getDescription(), avisClientDTO.getDescription());
		Assert.assertEquals(avisClientDTOList.get(0).getNote(), avisClientDTO.getNote());
		Assert.assertEquals(avisClientDTOList.get(0).getRefProduit(), avisClientDTO.getRefProduit());
	}

	@Test
	public void addAvisClient() {
		AvisClientDTO avisClientDTO = buildAvisClientDTO(3, "très bien", 4, "A07A07");

		when(avisClientBusiness.add(Mockito.any(AvisClientDTO.class))).thenReturn(avisClientDTO);

		AvisClientDTO retour = controllerAvisClient.addAvisClient(avisClientDTO);

		Assert.assertEquals(retour.getId(), avisClientDTO.getId());
		Assert.assertEquals(retour.getDescription(), avisClientDTO.getDescription());
		Assert.assertEquals(retour.getNote(), avisClientDTO.getNote());
		Assert.assertEquals(retour.getRefProduit(), avisClientDTO.getRefProduit());
	}

	@Test
	public void updateAvisClient() {
		AvisClientDTO avisClientDTO = buildAvisClientDTO(4, "null", 0, "A08A08");

		when(avisClientBusiness.update(Mockito.any(AvisClientDTO.class))).thenReturn(avisClientDTO);

		AvisClientDTO retour = controllerAvisClient.updateAvisClient(avisClientDTO);

		Assert.assertEquals(retour.getId(), avisClientDTO.getId());
		Assert.assertEquals(retour.getDescription(), avisClientDTO.getDescription());
		Assert.assertEquals(retour.getNote(), avisClientDTO.getNote());
		Assert.assertEquals(retour.getRefProduit(), avisClientDTO.getRefProduit());
	}

	@Test
	public void deleteAvisClient() {
		AvisClientDTO avisClientDTO = buildAvisClientDTO(5, "excellent", 5, "A09A09");

		when(avisClientBusiness.delete(avisClientDTO.getId())).thenReturn(Boolean.TRUE);

		Boolean retour = controllerAvisClient.deleteAvisClientById(avisClientDTO.getId());

		Assert.assertTrue(retour);
	}

}