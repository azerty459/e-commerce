package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PaginationDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.PaginationBusiness;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.CategorieRepositoryCustom;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

	@Test
	public void getPaginationEmpty() {
		PaginationDTO paginationDTO = paginationBusiness.getPagination("toto", 1, 5);
		Assert.assertNull(paginationDTO.getCategories());
		Assert.assertNull(paginationDTO.getProduits());
		Assert.assertEquals(paginationDTO.getPageActuelle(), 0);
		Assert.assertEquals(paginationDTO.getPageMax(), 0);
		Assert.assertEquals(paginationDTO.getPageMin(), 0);
		Assert.assertEquals(paginationDTO.getTotal(), 0);
	}

	@Test
	public void getPaginationByTypeProduit() {
		ArrayList<Produit> produitArrayList = new ArrayList<>();
		Produit produit = new Produit();
		produit.setCaracteristiques(new ArrayList<>());
		produit.setPhotos(new ArrayList<>());
		produit.setCategories(new ArrayList<>());
		produit.setReferenceProduit("A01A47");
		produit.setPrixHT(4.7);
		produit.setNom("Un test");
		produit.setDescription("");
		produitArrayList.add(produit);

		Mockito.when(page.getTotalPages()).thenReturn(5);
		Mockito.when(page.getTotalElements()).thenReturn(Long.valueOf(100));
		Mockito.when(page.getContent()).thenReturn(produitArrayList);
		Mockito.when(produitBusiness.getPage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);

		PaginationDTO paginationDTO = paginationBusiness.getPagination("produit", 2, 5);
		Mockito.verify(produitBusiness, Mockito.times(1)).getPage(Mockito.anyInt(), Mockito.anyInt());
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

		PaginationDTO paginationDTO = paginationBusiness.getPagination("categorie", 5, 50);
		Mockito.verify(categorieBusiness, Mockito.times(1)).getPage(Mockito.anyInt(), Mockito.anyInt());
		Assert.assertNotNull(paginationDTO);

		Assert.assertEquals(150,paginationDTO.getTotal());
		Assert.assertEquals(5, paginationDTO.getPageActuelle());
		Assert.assertEquals(1, paginationDTO.getPageMin());
		Assert.assertEquals(8, paginationDTO.getPageMax());
		Assert.assertEquals(1, paginationDTO.getCategories().size());
		Assert.assertEquals(new ArrayList<Produit>(), paginationDTO.getProduits());
	}
}