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
	private CategorieBusiness categorieBusiness2;

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

		when(categorieBusiness2.getCategorie(Mockito.anyInt(), Mockito.any(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(categorieDTOList);

		List<CategorieDTO> listRetour = new ArrayList<>(controllerCategorie.getAllCategorie());

		Assert.assertEquals(categorieDTOList.size(), listRetour.size());


//		Assert.assertEquals(listRetour.get(0).getId(), categorieDTO_1.getId());
//		Assert.assertEquals(listRetour.get(0).getNom(), categorieDTO_1.getNom());
//		Assert.assertEquals(listRetour.get(0).getLevel(), categorieDTO_1.getLevel());
//		Assert.assertEquals(listRetour.get(0).getProfondeur(), categorieDTO_1.getProfondeur());
//
//		Assert.assertEquals(listRetour.get(1).getId(), categorieDTO_2.getId());
//		Assert.assertEquals(listRetour.get(1).getNom(), categorieDTO_2.getNom());
//		Assert.assertEquals(listRetour.get(1).getLevel(), categorieDTO_2.getLevel());
//		Assert.assertEquals(listRetour.get(1).getProfondeur(), categorieDTO_2.getProfondeur());
	}

}
