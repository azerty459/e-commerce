package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.persistance.entity.AvisClient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class AvisClientTransformerTests {

	@Test
	public void singleDtoToEntity() {
		AvisClientDTO avisClientDTO = this.getAvisClientDTO();
		AvisClient avisClient = AvisClientTransformer.dtoToEntity(avisClientDTO);

		this.assertData(avisClient, avisClientDTO);
	}

	@Test
	public void singleEntityToDto() {
		AvisClient avisClient = this.getAvisClient();
		AvisClientDTO avisClientDTO = AvisClientTransformer.entityToDto(avisClient);

		this.assertData(avisClient, avisClientDTO);
	}

	@Test
	public void severalDtoToEntity() {
		List<AvisClientDTO> avisClientDTOS = this.getListeAvisClientDTO();
		List<AvisClient> listeAvisClient = new ArrayList<>(AvisClientTransformer.listDtoToEntity(avisClientDTOS));

		Assert.assertNotNull(listeAvisClient);
		this.assertData(listeAvisClient.get(0), avisClientDTOS.get(0));
		this.assertData(listeAvisClient.get(1), avisClientDTOS.get(1));
	}

	@Test
	public void severalDtoToEntity_null() {
		List<AvisClient> listeAvisClient = new ArrayList<>(AvisClientTransformer.listDtoToEntity(null));
		Assert.assertTrue(listeAvisClient.isEmpty());
	}

	@Test
	public void severalEntityToDto_null() {
		List<AvisClientDTO> avisClientDTOS = new ArrayList<>(AvisClientTransformer.listEntityToDto(null));
		Assert.assertTrue(avisClientDTOS.isEmpty());
	}

	@Test
	public void severalEntityToDto() {
		List<AvisClient> avisClients = this.getListeAvisClient();
		List<AvisClientDTO> avisClientDTOS = new ArrayList<>(AvisClientTransformer.listEntityToDto(avisClients));

		Assert.assertNotNull(avisClientDTOS);
		this.assertData(avisClients.get(0), avisClientDTOS.get(0));
		this.assertData(avisClients.get(1), avisClientDTOS.get(1));
	}

	private List<AvisClientDTO> getListeAvisClientDTO() {
		List<AvisClientDTO> liste = new ArrayList<>();
		liste.add(this.getAvisClientDTO());
		liste.add(this.getAvisClientDTO());
		return liste;
	}

	private List<AvisClient> getListeAvisClient() {
		List<AvisClient> liste = new ArrayList<>();
		liste.add(this.getAvisClient());
		liste.add(this.getAvisClient());
		return liste;
	}

	private AvisClientDTO getAvisClientDTO() {
		AvisClientDTO avisDTO = new AvisClientDTO();
		avisDTO.setId(new Random().nextInt(100));
		avisDTO.setNote(5);
		avisDTO.setDescription("Bla bla bla");
		avisDTO.setDate(LocalDateTime.now());
		return avisDTO;
	}

	private AvisClient getAvisClient() {
		AvisClient avis = new AvisClient();
		avis.setId(new Random().nextInt(100));
		avis.setNote(5);
		avis.setDescription("Bla bla bla");
		avis.setDate(LocalDateTime.now());
		return avis;
	}

	private void assertData(AvisClient avisClient, AvisClientDTO avisClientDTO) {
		Assert.assertNotNull(avisClient);
		Assert.assertNotNull(avisClientDTO);
		Assert.assertEquals(avisClient.getId(), avisClientDTO.getId());
		Assert.assertEquals(avisClient.getNote(), avisClientDTO.getNote());
		Assert.assertEquals(avisClient.getDate(), avisClientDTO.getDate());
		Assert.assertEquals(avisClient.getDescription(), avisClientDTO.getDescription());
		if (avisClient.getProduit() != null) {
			Assert.assertEquals(avisClient.getProduit().getReferenceProduit(), avisClientDTO.getRefProduit());
		}
	}

}
