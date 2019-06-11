package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.PaginationDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
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
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PaginationBusinessTests {

	@Mock
	private IProduitBusiness produitBusiness;

	@Mock
	private ICategorieBusiness categorieBusiness;

	@Mock
	private Page page;

	@InjectMocks
	private PaginationBusiness paginationBusiness;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@NotNull
	private ProduitDTO builProduitDTO(String ref, float prix, String nom, String description) {
		ProduitDTO produitDTO = new ProduitDTO();
		produitDTO.setPhotos(new ArrayList<>());
		produitDTO.setCategories(new ArrayList<>());
		produitDTO.setRef(ref);
		produitDTO.setPrixHT(prix);
		produitDTO.setNom(nom);
		produitDTO.setDescription(description);
		return produitDTO;
	}

	@Test
	public void getPaginationEmpty() {
		PaginationDTO paginationDTO = paginationBusiness.getPagination("toto", 1, 5, "toto", 0);
		Assert.assertNull(paginationDTO);
	}

	@Test
	public void getPaginationByTypeProduit() {
		ArrayList<Produit> produitArrayList = new ArrayList<>();
		Produit produit = new Produit();
		produit.setPhotos(new ArrayList<>());
		produit.setCategories(new ArrayList<>());
		produit.setReferenceProduit("A01A47");
		produit.setPrixHT(4.7f);
		produit.setNom("Un test");
		produit.setDescription("");
		produitArrayList.add(produit);

		Mockito.when(page.getTotalPages()).thenReturn(5);
		Mockito.when(page.getTotalElements()).thenReturn(Long.valueOf(100));
		Mockito.when(page.getContent()).thenReturn(produitArrayList);
		Mockito.when(produitBusiness.getPage(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt())).thenReturn(page);

		PaginationDTO paginationDTO = paginationBusiness.getPagination("produit", 2, 5, "toto", 0);
		Mockito.verify(produitBusiness, Mockito.times(1)).getPage(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
		Assert.assertNotNull(paginationDTO);

		Assert.assertEquals(100, paginationDTO.getTotal());
		Assert.assertEquals(2, paginationDTO.getPageActuelle());
		Assert.assertEquals(1, paginationDTO.getPageMin());
		Assert.assertEquals(5, paginationDTO.getPageMax());
		Assert.assertEquals(new ArrayList<Categorie>(), paginationDTO.getCategories());
		Assert.assertEquals(1, paginationDTO.getProduits().size());
	}

	@Test
	public void getPaginationByTypeCategorie() {
		ArrayList<Categorie> categorieArrayList = new ArrayList<>();
		Categorie categorie = new Categorie();
		categorie.setProduits(new ArrayList<>());
		categorie.setNomCategorie("Livre");
		categorie.setLevel(1);
		categorie.setBorneDroit(1);
		categorie.setBorneGauche(8);
		categorieArrayList.add(categorie);

		Mockito.when(page.getTotalPages()).thenReturn(8);
		Mockito.when(page.getTotalElements()).thenReturn(Long.valueOf(150));
		Mockito.when(page.getContent()).thenReturn(categorieArrayList);
		Mockito.when(categorieBusiness.getPage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);

		PaginationDTO paginationDTO = paginationBusiness.getPagination("categorie", 5, 50, "toto", 0);
		Mockito.verify(categorieBusiness, Mockito.times(1)).getPage(Mockito.anyInt(), Mockito.anyInt());
		Assert.assertNotNull(paginationDTO);

		Assert.assertEquals(150, paginationDTO.getTotal());
		Assert.assertEquals(5, paginationDTO.getPageActuelle());
		Assert.assertEquals(1, paginationDTO.getPageMin());
		Assert.assertEquals(8, paginationDTO.getPageMax());
		Assert.assertEquals(1, paginationDTO.getCategories().size());
		Assert.assertEquals(new ArrayList<Produit>(), paginationDTO.getProduits());
	}

	@Test
	public void getProductOrderByCriter() {
		List<ProduitDTO> produitDTOList = new ArrayList<>();
		ProduitDTO produitDTO_1 = builProduitDTO("A05A05", 14.5f, "BBBB", "ok");
		ProduitDTO produitDTO_2 = builProduitDTO("A06A06", 7.2f, "AAAA", "okK");
		produitDTOList.add(produitDTO_1);
		produitDTOList.add(produitDTO_2);

		List<ProduitDTO> retour;
		retour = paginationBusiness.getProduitOrderBy(produitDTOList, "Nom");
		Assert.assertEquals(produitDTO_2.getNom(), retour.get(0).getNom());

		retour = paginationBusiness.getProduitOrderBy(produitDTOList, "Prix croissant");
		Assert.assertEquals(produitDTO_2.getPrixHT(), retour.get(0).getPrixHT(), 0.1);

		retour = paginationBusiness.getProduitOrderBy(produitDTOList, "Prix décroissant");
		Assert.assertEquals(produitDTO_1.getPrixHT(), retour.get(0).getPrixHT(), 0.1);
	}


}
