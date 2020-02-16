package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class ProduitTransformerTests {

    @Test
    public void singleDtoToEntity() {
        ProduitDTO produitDTO = buildProduitDTO(1);

        Produit produit = ProduitTransformer.dtoToEntity(produitDTO);

        assertEquals(produit, produitDTO);
    }

    @Test
    public void singleEntityToDto() {
        Produit produit = buildProduit(1);

        ProduitDTO produitDTO = ProduitTransformer.entityToDto(produit);

        assertEquals(produit, produitDTO);
    }

    @Test
    public void severalDtoToEntity() {
        List<ProduitDTO> listeProduitDTO = Arrays.asList(buildProduitDTO(1), buildProduitDTO(2));
        List<Produit> listeProduit = new ArrayList<>(ProduitTransformer.dtoToEntity(listeProduitDTO));

        Assert.assertNotNull(listeProduit);
        Assert.assertEquals(listeProduitDTO.size(), listeProduit.size());

        assertEquals(listeProduit.get(0), listeProduitDTO.get(0));
    }

    @Test
    public void severalEntityToDto() {
        List<Produit> listeProduit = Arrays.asList(buildProduit(1), buildProduit(2));
        List<ProduitDTO> listeProduitDTO = new ArrayList<>(ProduitTransformer.entityToDto(listeProduit));

        Assert.assertNotNull(listeProduitDTO);
        Assert.assertEquals(listeProduit.size(), listeProduitDTO.size());

        assertEquals(listeProduit.get(0), listeProduitDTO.get(0));
    }

    private void assertEquals(Produit produit, ProduitDTO produitDTO) {
        Assert.assertNotNull(produit);
        Assert.assertNotNull(produitDTO);

        Assert.assertEquals(produit.getReferenceProduit(), produitDTO.getRef());
        Assert.assertEquals(produit.getDescription(), produitDTO.getDescription());
        Assert.assertEquals(produit.getNom(), produitDTO.getNom());
        Assert.assertEquals(produit.getPrixHT(), produitDTO.getPrixHT(), 0);

        Assert.assertNotNull(produit.getCategories());
        Assert.assertNotNull(produitDTO.getCategories());
        Assert.assertEquals(produit.getCategories().size(), produitDTO.getCategories().size());
        Assert.assertEquals(produit.getCategories().get(0).getNomCategorie(), produitDTO.getCategories().get(0).getNom());

        Assert.assertEquals(produit.getPhotoPrincipale().getIdPhoto(), produitDTO.getPhotoPrincipale().getId());
        Assert.assertEquals(produit.getPhotoPrincipale().getUrl(), produitDTO.getPhotoPrincipale().getUrl());

        Assert.assertNotNull(produit.getPhotos());
        Assert.assertNotNull(produitDTO.getPhotos());
        Assert.assertEquals(produit.getPhotos().size(), produitDTO.getPhotos().size());
    }

    // ------------------------- ENTITE -------------------------

    @NotNull
    private static Produit buildProduit(int number) {
        Produit produit = new Produit();
        produit.setReferenceProduit("A4224" + number);
        produit.setDescription("ceci est un test" + number);
        produit.setNom("test" + number);
        produit.setPrixHT(number);
        produit.setCategories(Collections.singletonList(buildCategorie()));

        Photo photo = buildPhoto();
        produit.setPhotoPrincipale(photo);
        produit.setPhotos(Collections.singletonList(photo));

        return produit;
    }

    @NotNull
    private static Categorie buildCategorie() {
        Categorie categorieDTO = new Categorie();
        categorieDTO.setNomCategorie("sous catégorie");
        return categorieDTO;
    }

    @NotNull
    private static Photo buildPhoto() {
        Photo photo = new Photo();
        photo.setIdPhoto(1);
        photo.setUrl("test");
        return photo;
    }

    // ------------------------- DTO -------------------------

    @NotNull
    private static ProduitDTO buildProduitDTO(int number) {
        ProduitDTO produitDto = new ProduitDTO();
        produitDto.setRef("A4224" + number);
        produitDto.setDescription("ceci est un test" + number);
        produitDto.setNom("test" + number);
        produitDto.setPrixHT(number);
        produitDto.setCategories(Collections.singletonList(buildCategorieDTOAvecSousCagorie()));

        PhotoDTO photoDTO = buildPhotoDTO();
        produitDto.setPhotoPrincipale(photoDTO);
        produitDto.setPhotos(Collections.singletonList(photoDTO));

        return produitDto;
    }

    @NotNull
    private static CategorieDTO buildCategorieDTOAvecSousCagorie() {
        CategorieDTO sousCategorieDTO = new CategorieDTO();
        sousCategorieDTO.setNom("sous catégorie");

        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setNom("catégorie");
        categorieDTO.setSousCategories(Collections.singletonList(sousCategorieDTO));

        return categorieDTO;
    }

    @NotNull
    private static PhotoDTO buildPhotoDTO() {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setIdPhoto(1);
        photoDTO.setUrl("test");
        return photoDTO;
    }
}