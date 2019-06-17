package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.CategorieSupprimeBusiness;
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
public class ControllerCategorieTests {


	@Mock
	private CategorieBusiness categorieBusiness;

	@Mock
	private CategorieSupprimeBusiness categorieSupprimeBusiness;

	@InjectMocks
	private ControllerCategorie controllerCategorie;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@NotNull
	private CategorieDTO buildCategorieDTO(int id, String nom, int level, int profondeur) {
		List<CategorieDTO> subCategorie = new ArrayList<>();
		CategorieDTO parent = new CategorieDTO();

		CategorieDTO categorieDTO = new CategorieDTO();
		categorieDTO.setId(id);
		categorieDTO.setNom(nom);
		categorieDTO.setLevel(level);
		categorieDTO.setProfondeur(profondeur);
		categorieDTO.setSousCategories(subCategorie);
		categorieDTO.setParent(parent);

		return categorieDTO;
	}

	@Test
	public void getAllCategorie() {
		CategorieDTO categorieDTO_1 = buildCategorieDTO(1, "gimp", 1, 3);
		CategorieDTO categorieDTO_2 = buildCategorieDTO(2, "photoshop", 2, 3);

		List<CategorieDTO> categorieDTOList = new ArrayList<>();
		categorieDTOList.add(categorieDTO_1);
		categorieDTOList.add(categorieDTO_2);

		Assert.assertEquals(categorieDTOList.size(), 2);

		when(categorieBusiness.getCategorie(Mockito.anyInt(), Mockito.any(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(categorieDTOList);

		List<CategorieDTO> listRetour = new ArrayList<>(controllerCategorie.getAllCategorie());

		Assert.assertEquals(listRetour.size(), categorieDTOList.size());
		Assert.assertEquals(listRetour.get(0).getId(), categorieDTO_1.getId());
		Assert.assertEquals(listRetour.get(0).getNom(), categorieDTO_1.getNom());
		Assert.assertEquals(listRetour.get(0).getLevel(), categorieDTO_1.getLevel());
		Assert.assertEquals(listRetour.get(0).getProfondeur(), categorieDTO_1.getProfondeur());

		Assert.assertEquals(listRetour.get(1).getId(), categorieDTO_2.getId());
		Assert.assertEquals(listRetour.get(1).getNom(), categorieDTO_2.getNom());
		Assert.assertEquals(listRetour.get(1).getLevel(), categorieDTO_2.getLevel());
		Assert.assertEquals(listRetour.get(1).getProfondeur(), categorieDTO_2.getProfondeur());
	}

	@Test
	public void getCategorieById() {
		CategorieDTO categorieDTO = buildCategorieDTO(3, "dessin", 2, 4);
		List<CategorieDTO> categorieDTOList = new ArrayList<>();
		categorieDTOList.add(categorieDTO);

		when(categorieBusiness.getCategorie(Mockito.anyInt(), Mockito.any(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(categorieDTOList);

		List<CategorieDTO> listRetour = new ArrayList<>(controllerCategorie.getCategorieById(categorieDTO.getId()));
		Assert.assertEquals(listRetour.size(), categorieDTOList.size());
		Assert.assertEquals(listRetour.get(0).getId(), categorieDTO.getId());
		Assert.assertEquals(listRetour.get(0).getNom(), categorieDTO.getNom());
		Assert.assertEquals(listRetour.get(0).getLevel(), categorieDTO.getLevel());
		Assert.assertEquals(listRetour.get(0).getProfondeur(), categorieDTO.getProfondeur());
	}

	@Test
	public void getSubCategory() {
		CategorieDTO categorieDTO = buildCategorieDTO(3, "dessin", 3, 4);
		categorieDTO.getSousCategories().add(buildCategorieDTO(4, "photoshop", 1, 2));

		List<CategorieDTO> categorieDTOList = new ArrayList<>();
		categorieDTOList.add(categorieDTO);

		when(categorieBusiness.getCategorie(Mockito.anyInt(), Mockito.any(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(categorieDTO.getSousCategories());

		List<CategorieDTO> listRetour = new ArrayList<>(controllerCategorie.getSubCategory(categorieDTO.getNom()));
		Assert.assertEquals(listRetour.size(), categorieDTOList.size());
		Assert.assertEquals(listRetour.get(0).getId(), categorieDTO.getSousCategories().get(0).getId());
		Assert.assertEquals(listRetour.get(0).getNom(), categorieDTO.getSousCategories().get(0).getNom());
		Assert.assertEquals(listRetour.get(0).getLevel(), categorieDTO.getSousCategories().get(0).getLevel());
		Assert.assertEquals(listRetour.get(0).getProfondeur(), categorieDTO.getSousCategories().get(0).getProfondeur());
	}

	@Test
	public void deleteCategoryById() {
		CategorieDTO categorieDTO = buildCategorieDTO(5, "gimp", 3, 4);
		when(categorieBusiness.delete(Mockito.anyInt())).thenReturn(Boolean.TRUE);

		Boolean bool = controllerCategorie.deleteCategorieById(categorieDTO.getId());

		Assert.assertTrue(bool);
	}

	@Test
	public void updateCategorieDTO() {
		CategorieDTO categorieDTO = buildCategorieDTO(6, "nimp", 5, 6);

		when(categorieBusiness.updateCategorie(Mockito.anyInt(), Mockito.anyString())).thenReturn(categorieDTO);

		CategorieDTO retour = controllerCategorie.updateCategorieDTO(categorieDTO.getId(), categorieDTO.getNom());

		Assert.assertNotNull(retour);
		Assert.assertEquals(retour.getId(), categorieDTO.getId());
		Assert.assertEquals(retour.getNom(), categorieDTO.getNom());
		Assert.assertEquals(retour.getLevel(), categorieDTO.getLevel());
		Assert.assertEquals(retour.getProfondeur(), categorieDTO.getProfondeur());
	}

	@Test
	public void moveCategorie() {
		CategorieDTO categorieDTO_1 = buildCategorieDTO(7, "photo", 1, 2);
		CategorieDTO categorieDTO_2 = buildCategorieDTO(8, "gimp", 2, 3);

		when(categorieBusiness.moveCategorie(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Boolean.TRUE);

		Boolean bool = controllerCategorie.moveCategorie(categorieDTO_1.getId(), categorieDTO_2.getId());

		Assert.assertTrue(bool);
	}

	@Test
	public void restoreCategorie() {
		CategorieDTO categorieDTO = buildCategorieDTO(1, "gimp", 1, 2);
		List<CategorieDTO> categorieDTOList = new ArrayList<>();
		categorieDTOList.add(categorieDTO);

		when(categorieSupprimeBusiness.restoreLastDeletedCategorie(Mockito.anyInt())).thenReturn(categorieDTOList);

		List<CategorieDTO> listRetour = new ArrayList<>(controllerCategorie.restoreCategorie(categorieDTO.getId()));

		Assert.assertNotNull(categorieDTO);
		Assert.assertEquals(listRetour.size(), categorieDTOList.size());
		Assert.assertEquals(listRetour.get(0).getId(), categorieDTOList.get(0).getId());
		Assert.assertEquals(listRetour.get(0).getNom(), categorieDTOList.get(0).getNom());
		Assert.assertEquals(listRetour.get(0).getLevel(), categorieDTOList.get(0).getLevel());
		Assert.assertEquals(listRetour.get(0).getProfondeur(), categorieDTOList.get(0).getProfondeur());
	}

	@Test
	public void addCategorieParent() {
		CategorieDTO categorieDTO = buildCategorieDTO(8, "toto", 2, 4);

		when(categorieBusiness.addParent(Mockito.anyString())).thenReturn(categorieDTO);

		CategorieDTO retour = controllerCategorie.addCategorieParent(categorieDTO.getNom());

		Assert.assertNotNull(retour);
		Assert.assertEquals(retour.getId(), categorieDTO.getId());
		Assert.assertEquals(retour.getNom(), categorieDTO.getNom());
		Assert.assertEquals(retour.getLevel(), categorieDTO.getLevel());
		Assert.assertEquals(retour.getProfondeur(), categorieDTO.getProfondeur());
	}

	@Test
	public void addCategorieEnfant() {
		CategorieDTO categorieDTO = buildCategorieDTO(18, "tata", 4, 4);

		when(categorieBusiness.addEnfant(Mockito.anyString(), Mockito.anyInt())).thenReturn(categorieDTO);

		CategorieDTO retour = controllerCategorie.addCategorieEnfant(categorieDTO.getNom(), categorieDTO.getId());

		Assert.assertNotNull(retour);
		Assert.assertEquals(retour.getId(), categorieDTO.getId());
		Assert.assertEquals(retour.getNom(), categorieDTO.getNom());
		Assert.assertEquals(retour.getLevel(), categorieDTO.getLevel());
		Assert.assertEquals(retour.getProfondeur(), categorieDTO.getProfondeur());
	}

}
