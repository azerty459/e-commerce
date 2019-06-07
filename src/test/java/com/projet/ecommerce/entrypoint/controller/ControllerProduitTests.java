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
	public void getAllProduct() {
		ProduitDTO produitDTO_1 = buildProduitDTO("AAA", "livre", "tarzan", 13.2f, 4f);
		ProduitDTO produitDTO_2 = buildProduitDTO("BBB", "livre", "tarzan", 13.2f, 4f);
		List<ProduitDTO> produitDTOList = new ArrayList<>();
		produitDTOList.add(produitDTO_1);
		produitDTOList.add(produitDTO_2);

		when(produitBusiness.getAll()).thenReturn(produitDTOList);

		List<ProduitDTO> listRetour = controllerProduit.getAllProduct();

		Assert.assertNotNull(listRetour);
		Assert.assertEquals(2, listRetour.size());
		Assert.assertEquals(produitDTO_1.getRef(), listRetour.get(0).getRef());
		Assert.assertEquals(produitDTO_1.getNom(), listRetour.get(0).getNom());
		Assert.assertEquals(produitDTO_1.getDescription(), listRetour.get(0).getDescription());
		Assert.assertEquals(produitDTO_1.getPrixHT(), listRetour.get(0).getPrixHT(), 0.1);
		Assert.assertEquals(produitDTO_1.getNoteMoyenne(), listRetour.get(0).getNoteMoyenne(), 0.1);
		Assert.assertEquals(produitDTO_1.getCategories().size(), listRetour.get(0).getCategories().size());
		Assert.assertEquals(produitDTO_1.getPhotos().size(), listRetour.get(0).getPhotos().size());
		Assert.assertEquals(produitDTO_1.getPhotoPrincipale(), listRetour.get(0).getPhotoPrincipale());

		Assert.assertEquals(2, listRetour.size());
		Assert.assertEquals(produitDTO_2.getRef(), listRetour.get(1).getRef());
		Assert.assertEquals(produitDTO_2.getNom(), listRetour.get(1).getNom());
		Assert.assertEquals(produitDTO_2.getDescription(), listRetour.get(1).getDescription());
		Assert.assertEquals(produitDTO_2.getPrixHT(), listRetour.get(1).getPrixHT(), 0.1);
		Assert.assertEquals(produitDTO_2.getNoteMoyenne(), listRetour.get(1).getNoteMoyenne(), 0.1);
		Assert.assertEquals(produitDTO_2.getCategories().size(), listRetour.get(1).getCategories().size());
		Assert.assertEquals(produitDTO_2.getPhotos().size(), listRetour.get(1).getPhotos().size());
		Assert.assertEquals(produitDTO_2.getPhotoPrincipale(), listRetour.get(1).getPhotoPrincipale());
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

	@Test
	public void createProduct() {
		ProduitDTO produitDTO = buildProduitDTO("AAA", "livre", "tarzan", 13.2f, 4f);

		when(produitBusiness.add(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyFloat(), Mockito.anyList())).thenReturn(produitDTO);

		ProduitDTO retour = controllerProduit.createProduct(produitDTO);

		Assert.assertNotNull(retour);
		Assert.assertEquals(produitDTO.getRef(), retour.getRef());
		Assert.assertEquals(produitDTO.getNom(), retour.getNom());
		Assert.assertEquals(produitDTO.getDescription(), retour.getDescription());
		Assert.assertEquals(produitDTO.getPrixHT(), retour.getPrixHT(), 0.1);
		Assert.assertEquals(produitDTO.getNoteMoyenne(), retour.getNoteMoyenne(), 0.1);
		Assert.assertNotNull(produitDTO.getCategories());
		Assert.assertNotNull(produitDTO.getPhotos());
		Assert.assertNotNull(produitDTO.getPhotoPrincipale());
	}

}
