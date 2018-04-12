package com.projet.ecommerce.business.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.*;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProduitTransformerTests {

    private TypeCaracteristique typeCaracteristique;
    private CaracteristiqueDTO caracteristiqueDTO;
    private Caracteristique caracteristique;
    private ArrayList<CaracteristiqueDTO> listeCaracteristiquesDto;
    private ArrayList<Caracteristique> listeCaracteristiques;
    private CategorieDTO sousCategorieDTO, categorieDTO;
    private Categorie sousCategorie, categorie;
    private ArrayList<CategorieDTO> listeCategoriesDto1, listeCategoriesDto2;
    private ArrayList<Categorie> listeCategories1, listeCategories2;
    private PhotoDTO photoDto;
    private Photo photo;
    private ArrayList<Photo> listePhoto;
    private ArrayList<PhotoDTO> listePhotoDTO;
    private ProduitDTO produitDTO1, produitDTO2;
    private Produit produit1, produit2;
    private List<Produit> listeProduits;
    private List<ProduitDTO> listeProduitsDTO;

    @Before
    public void setUp(){
        typeCaracteristique = new TypeCaracteristique();
        typeCaracteristique.setIdTypeCaracteristique(0);
        typeCaracteristique.setType("testType");

        caracteristiqueDTO = new CaracteristiqueDTO();
        caracteristiqueDTO.setValeur("testCar");
        caracteristiqueDTO.setTypeCaracteristique(typeCaracteristique);

        caracteristique = new Caracteristique();
        caracteristique.setValeur("testCar");
        caracteristique.setTypeCaracteristique(typeCaracteristique);

        listeCaracteristiquesDto = new ArrayList<>();
        listeCaracteristiquesDto.add(caracteristiqueDTO);

        listeCaracteristiques = new ArrayList<>();
        listeCaracteristiques.add(caracteristique);

        sousCategorieDTO = new CategorieDTO();
        categorieDTO = new CategorieDTO();
        listeCategoriesDto1 = new ArrayList<>();
        listeCategoriesDto2 = new ArrayList<>();
        sousCategorieDTO.setNom("sous catégorie");
        listeCategoriesDto1.add(sousCategorieDTO);
        categorieDTO.setNom("catégorie");
        categorieDTO.setSousCategories(listeCategoriesDto1);
        listeCategoriesDto2.add(categorieDTO);

        sousCategorie = new Categorie();
        categorie = new Categorie();
        listeCategories1 = new ArrayList<>();

        photoDto = new PhotoDTO();
        listePhotoDTO = new ArrayList<>();
        photoDto.setIdPhoto(1);
        photoDto.setUrl("test");
        listePhotoDTO.add(photoDto);

        photo = new Photo();
        listePhoto = new ArrayList<>();
        photo.setIdPhoto(1);
        photo.setUrl("test");
        listePhoto.add(photo);

        produitDTO1 = new ProduitDTO();
        produitDTO1.setCaracteristiques(listeCaracteristiquesDto);
        produitDTO1.setCategories(listeCategoriesDto2);
        produitDTO1.setPhotos(listePhotoDTO);
        produitDTO1.setDescription("ceci est un test");
        produitDTO1.setNom("test");
        produitDTO1.setPrixHT(1);
        produitDTO1.setRef("A4224");

        produitDTO2 = new ProduitDTO();
        produitDTO2.setCaracteristiques(listeCaracteristiquesDto);
        produitDTO2.setCategories(listeCategoriesDto2);
        produitDTO2.setPhotos(listePhotoDTO);
        produitDTO2.setDescription("ceci est un test");
        produitDTO2.setNom("test");
        produitDTO2.setPrixHT(1);
        produitDTO2.setRef("A4224");

        produit1 = new Produit();
        produit1.setCaracteristiques(listeCaracteristiques);
        produit1.setCategories(listeCategories1);
        produit1.setPhotos(listePhoto);
        produit1.setDescription("ceci est un test");
        produit1.setNom("test");
        produit1.setPrixHT(1);
        produit1.setReferenceProduit("A4224");

        produit2 = new Produit();
        produit2.setCaracteristiques(listeCaracteristiques);
        produit2.setCategories(listeCategories1);
        produit2.setPhotos(listePhoto);
        produit2.setDescription("ceci est un test");
        produit2.setNom("test");
        produit2.setPrixHT(1);
        produit2.setReferenceProduit("A4224");

        listeProduits = new ArrayList<>();
        listeProduits.add(produit1);
        listeProduits.add(produit2);

        listeProduitsDTO = new ArrayList<>();
        listeProduitsDTO.add(produitDTO1);
        listeProduitsDTO.add(produitDTO2);
    }

    @Test
    public void singleDtoToEntity(){
        Produit prod = ProduitTransformer.dtoToEntity(produitDTO1);
        Assert.assertNotNull(prod);
        Assert.assertEquals(produitDTO1.getCaracteristiques().get(0).getValeur(), prod.getCaracteristiques().get(0).getValeur());
        Assert.assertEquals(produitDTO1.getCaracteristiques().get(0).getTypeCaracteristique(), prod.getCaracteristiques().get(0).getTypeCaracteristique());
        Assert.assertEquals(produitDTO1.getCategories().get(0).getNom(), prod.getCategories().get(0).getNomCategorie());
        Assert.assertEquals(produitDTO1.getDescription(), prod.getDescription());
        Assert.assertEquals(produitDTO1.getNom(), prod.getNom());
        Assert.assertEquals(produitDTO1.getPhotos().get(0).getIdPhoto(), prod.getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(produitDTO1.getPhotos().get(0).getUrl(), prod.getPhotos().get(0).getUrl());
        Assert.assertEquals(produitDTO1.getPrixHT(), prod.getPrixHT(), 0);
        Assert.assertEquals(produitDTO1.getRef(), prod.getReferenceProduit());
    }

    @Test
    public void singleEntityToDto(){
        ProduitDTO prodDto = ProduitTransformer.entityToDTO(produit1);
        Assert.assertNotNull(prodDto);
        Assert.assertEquals(produit1.getCaracteristiques().get(0).getValeur(), prodDto.getCaracteristiques().get(0).getValeur());
        Assert.assertEquals(produit1.getCaracteristiques().get(0).getTypeCaracteristique(), prodDto.getCaracteristiques().get(0).getTypeCaracteristique());
        Assert.assertEquals(produit1.getCategories(), prodDto.getCategories());
        Assert.assertEquals(produit1.getDescription(), prodDto.getDescription());
        Assert.assertEquals(produit1.getNom(), prodDto.getNom());
        Assert.assertEquals(produit1.getPhotos().get(0).getIdPhoto(), prodDto.getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(produit1.getPhotos().get(0).getUrl(), prodDto.getPhotos().get(0).getUrl());
        Assert.assertEquals(produit1.getPrixHT(), prodDto.getPrixHT(), 0);
        Assert.assertEquals(produit1.getReferenceProduit(), prodDto.getRef());
    }

    @Test
    public void severalDtoToEntity(){
        List<Produit> listProd = ProduitTransformer.dtoToEntity(listeProduitsDTO);

        Assert.assertNotNull(listProd);
        Assert.assertEquals(listProd.get(0).getCaracteristiques().get(0).getValeur(), listeProduitsDTO.get(0).getCaracteristiques().get(0).getValeur());
        Assert.assertEquals(listProd.get(0).getCaracteristiques().get(0).getTypeCaracteristique(), listeProduitsDTO.get(0).getCaracteristiques().get(0).getTypeCaracteristique());
        Assert.assertEquals(listProd.get(0).getCategories().get(0).getNomCategorie(), listeProduitsDTO.get(0).getCategories().get(0).getNom());
        Assert.assertEquals(listProd.get(0).getDescription(), listeProduitsDTO.get(0).getDescription());
        Assert.assertEquals(listProd.get(0).getNom(), listeProduitsDTO.get(0).getNom());
        Assert.assertEquals(listProd.get(0).getPhotos().get(0).getIdPhoto(), listeProduitsDTO.get(0).getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(listProd.get(0).getPhotos().get(0).getUrl(), listeProduitsDTO.get(0).getPhotos().get(0).getUrl());
        Assert.assertEquals(listProd.get(0).getPrixHT(), listeProduitsDTO.get(0).getPrixHT(), 0);
        Assert.assertEquals(listProd.get(0).getReferenceProduit(), listeProduitsDTO.get(0).getRef());
    }

    @Test
    public void severalEntityToDto(){
        List<ProduitDTO> listDTO = ProduitTransformer.entityToDTO(listeProduits);

        Assert.assertNotNull(listDTO);
        Assert.assertEquals(listDTO.get(0).getCaracteristiques().get(0).getTypeCaracteristique(), listeProduits.get(0).getCaracteristiques().get(0).getTypeCaracteristique());
        Assert.assertEquals(listDTO.get(0).getCaracteristiques().get(0).getValeur(), listeProduits.get(0).getCaracteristiques().get(0).getValeur());
        Assert.assertEquals(listDTO.get(0).getCategories(), listeProduits.get(0).getCategories());
        Assert.assertEquals(listDTO.get(0).getDescription(), listeProduits.get(0).getDescription());
        Assert.assertEquals(listDTO.get(0).getNom(), listeProduits.get(0).getNom());
        Assert.assertEquals(listDTO.get(0).getPhotos().get(0).getIdPhoto(), listeProduits.get(0).getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(listDTO.get(0).getPhotos().get(0).getUrl(), listeProduits.get(0).getPhotos().get(0).getUrl());
        Assert.assertEquals(listDTO.get(0).getPrixHT(), listeProduits.get(0).getPrixHT(), 0);
        Assert.assertEquals(listDTO.get(0).getRef(), listeProduits.get(0).getReferenceProduit());
    }
}