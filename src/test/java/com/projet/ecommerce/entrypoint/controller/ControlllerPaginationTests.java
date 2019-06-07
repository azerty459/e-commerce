package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PaginationDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.impl.PaginationBusiness;
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
public class ControlllerPaginationTests {

	@Mock
	private PaginationBusiness paginationBusiness;

	@InjectMocks
	private ControllerPagination controllerPagination;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@NotNull
	private PaginationDTO buildPaginationDTO(int pageActuelle, int pageMin, int pageMax, long total) {
		List<ProduitDTO> produitDTOList = new ArrayList<>();
		List<CategorieDTO> categorieDTOList = new ArrayList<>();
		List<UtilisateurDTO> utilisateurDTOList = new ArrayList<>();

		PaginationDTO paginationDTO = new PaginationDTO();
		paginationDTO.setPageActuelle(pageActuelle);
		paginationDTO.setPageMin(pageMin);
		paginationDTO.setPageMax(pageMax);
		paginationDTO.setTotal(total);
		paginationDTO.setProduits(produitDTOList);
		paginationDTO.setCategories(categorieDTOList);
		paginationDTO.setUtilisateurs(utilisateurDTOList);

		return paginationDTO;
	}

	@Test
	public void getPaginationProduct() {

		PaginationDTO paginationDTO = buildPaginationDTO(3, 1, 10, 20);

		when(paginationBusiness.getPagination(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt())).thenReturn(paginationDTO);

		PaginationDTO paginationDTORetour = controllerPagination.getPaginationProduct("produit", paginationDTO.getPageActuelle(), paginationDTO.getPageMax(), "livre", 1);

		Assert.assertNotNull(paginationDTORetour);
		Assert.assertEquals(paginationDTO.getPageActuelle(), paginationDTORetour.getPageActuelle());
		Assert.assertEquals(paginationDTO.getPageMin(), paginationDTORetour.getPageMin());
		Assert.assertEquals(paginationDTO.getPageMax(), paginationDTORetour.getPageMax());
		Assert.assertEquals(paginationDTO.getTotal(), paginationDTORetour.getTotal());
		Assert.assertNotNull(paginationDTORetour.getProduits());
		Assert.assertNotNull(paginationDTORetour.getCategories());
		Assert.assertNotNull(paginationDTORetour.getUtilisateurs());
	}

	@Test
	public void getPaginationProduct2() {

		PaginationDTO paginationDTO = buildPaginationDTO(3, 1, 10, 20);

		when(paginationBusiness.getPagination(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt())).thenReturn(paginationDTO);

		PaginationDTO paginationDTORetour = controllerPagination.getPaginationProduct("utilisateur", paginationDTO.getPageActuelle(), paginationDTO.getPageMax());

		Assert.assertNotNull(paginationDTORetour);
		Assert.assertEquals(paginationDTO.getPageActuelle(), paginationDTORetour.getPageActuelle());
		Assert.assertEquals(paginationDTO.getPageMin(), paginationDTORetour.getPageMin());
		Assert.assertEquals(paginationDTO.getPageMax(), paginationDTORetour.getPageMax());
		Assert.assertEquals(paginationDTO.getTotal(), paginationDTORetour.getTotal());
		Assert.assertNotNull(paginationDTORetour.getProduits());
		Assert.assertNotNull(paginationDTORetour.getCategories());
		Assert.assertNotNull(paginationDTORetour.getUtilisateurs());
	}

}
