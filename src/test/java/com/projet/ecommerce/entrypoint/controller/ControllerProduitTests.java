package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.persistance.entity.Produit;
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
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ControllerProduitTests {

	@Mock
	private ProduitBusiness produitBusiness;

	@InjectMocks
	private ControllerProduit controllerProduit;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@NotNull
	private ProduitDTO buildProduitDTO(String ref, String nom, String description, float prixHT, float noteMoyenne) {
		List<CategorieDTO> categorieDTOList = new ArrayList<>();
		List<PhotoDTO> photoDTOList = new ArrayList<>();
		PhotoDTO photoDTO = new PhotoDTO();

		ProduitDTO produitDTO = new ProduitDTO();
		produitDTO.setRef(ref);
		produitDTO.setNom(nom);
		produitDTO.setDescription(description);
		produitDTO.setPrixHT(prixHT);
		produitDTO.setNoteMoyenne(noteMoyenne);
		produitDTO.setCategories(categorieDTOList);
		produitDTO.setPhotos(photoDTOList);
		produitDTO.setPhotoPrincipale(photoDTO);

		return produitDTO;
	}

	@Test
	public void getAllProductByRef() {
		ProduitDTO produitDTO = buildProduitDTO("AAA", "livre", "tarzan", 13.2f, 4f);
		when(produitBusiness.getByRef(produitDTO.getRef())).thenReturn(produitDTO);

		ProduitDTO retour = controllerProduit.getProduct(produitDTO.getRef());
		Assert.assertNotNull(retour);
		Assert.assertEquals(produitDTO.getRef(), retour.getRef());
		Assert.assertEquals(produitDTO.getNom(), retour.getNom());
		Assert.assertEquals(produitDTO.getDescription(), retour.getDescription());
		Assert.assertEquals(produitDTO.getPrixHT(), retour.getPrixHT(), 0.1);
		Assert.assertEquals(produitDTO.getNoteMoyenne(), retour.getNoteMoyenne(), 0.1);
		Assert.assertNotNull(produitDTO.getCategories());
		Assert.assertNotNull(produitDTO.getPhotos());
		Assert.assertNotNull(produitDTO.getPhotoPrincipale());

		Assert.assertEquals(0, produitDTO.getCategories().size());
		Assert.assertEquals(0, produitDTO.getPhotos().size());
	}

	@Test
	public void updateProduct() {
		ProduitDTO produitDTO = buildProduitDTO("AAA", "livre", "tarzan", 13.2f, 4f);
		when(produitBusiness.update(Mockito.any(Produit.class))).thenReturn(produitDTO);

		ProduitDTO retour = controllerProduit.updateProduit(produitDTO);

		Assert.assertNotNull(retour);
		Assert.assertEquals(produitDTO.getRef(), retour.getRef());
		Assert.assertEquals(produitDTO.getNom(), retour.getNom());
		Assert.assertEquals(produitDTO.getDescription(), retour.getDescription());
		Assert.assertEquals(produitDTO.getPrixHT(), retour.getPrixHT(), 0.1);
		Assert.assertEquals(produitDTO.getNoteMoyenne(), retour.getNoteMoyenne(), 0.1);
		Assert.assertNotNull(produitDTO.getCategories());
		Assert.assertNotNull(produitDTO.getPhotos());
		Assert.assertNotNull(produitDTO.getPhotoPrincipale());

		Assert.assertEquals(0, produitDTO.getCategories().size());
		Assert.assertEquals(0, produitDTO.getPhotos().size());
	}

}
