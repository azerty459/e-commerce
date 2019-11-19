package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProduitTransformerTests {

    @Test
    public void singleDtoToEntity() {
        ProduitDTO produitDTO = buildProduitDTO();
        Produit prod = ProduitTransformer.dtoToEntity(produitDTO);
        Assert.assertNotNull(prod);
        Assert.assertEquals(produitDTO.getCategories().get(0).getNom(), prod.getCategories().get(0).getNomCategorie());
        Assert.assertEquals(produitDTO.getDescription(), prod.getDescription());
        Assert.assertEquals(produitDTO.getNom(), prod.getNom());
        Assert.assertEquals(produitDTO.getPhotos().get(0).getId(), prod.getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(produitDTO.getPhotos().get(0).getUrl(), prod.getPhotos().get(0).getUrl());
        Assert.assertEquals(produitDTO.getPrixHT(), prod.getPrixHT(), 0);
        Assert.assertEquals(produitDTO.getRef(), prod.getReferenceProduit());
    }

    @Test
    public void singleEntityToDto() {
        Produit produit = buildProduitEntity();
        ProduitDTO prodDto = ProduitTransformer.entityToDto(produit);
        Assert.assertNotNull(prodDto);
        //Assert.assertTrue(produit.getCategories().containsAll(prodDto.getCategories()));
        Assert.assertEquals(produit.getDescription(), prodDto.getDescription());
        Assert.assertEquals(produit.getNom(), prodDto.getNom());
        Assert.assertEquals(produit.getPhotos().get(0).getIdPhoto(), prodDto.getPhotos().get(0).getId());
        Assert.assertEquals(produit.getPhotos().get(0).getUrl(), prodDto.getPhotos().get(0).getUrl());
        Assert.assertEquals(produit.getPrixHT(), prodDto.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), prodDto.getRef());
    }

    @Test
    public void severalDtoToEntity() {
        List<ProduitDTO> produitDTOList = new ArrayList<>();
        ProduitDTO produitDTO = buildProduitDTO();
        produitDTOList.add(produitDTO);
        List<Produit> listProd = new ArrayList<>(ProduitTransformer.dtoToEntity(produitDTOList));

        Assert.assertNotNull(listProd);
        Assert.assertEquals(listProd.get(0).getCategories().get(0).getNomCategorie(), produitDTOList.get(0).getCategories().get(0).getNom());
        Assert.assertEquals(listProd.get(0).getDescription(), produitDTOList.get(0).getDescription());
        Assert.assertEquals(listProd.get(0).getNom(), produitDTOList.get(0).getNom());
        Assert.assertEquals(listProd.get(0).getPhotos().get(0).getIdPhoto(), produitDTOList.get(0).getPhotos().get(0).getId());
        Assert.assertEquals(listProd.get(0).getPhotos().get(0).getUrl(), produitDTOList.get(0).getPhotos().get(0).getUrl());
        Assert.assertEquals(listProd.get(0).getPrixHT(), produitDTOList.get(0).getPrixHT(), 0);
        Assert.assertEquals(listProd.get(0).getReferenceProduit(), produitDTOList.get(0).getRef());
    }

    @Test
    public void severalEntityToDto() {
        List<Produit> produitList = new ArrayList<>();
        Produit produit = buildProduitEntity();
        produitList.add(produit);
        List<ProduitDTO> listDTO = new ArrayList<>(ProduitTransformer.entityToDto(produitList));

        Assert.assertNotNull(listDTO);
        //Assert.assertEquals(listDTO.get(0).getCategories(), produitList.get(0).getCategories());
        Assert.assertEquals(listDTO.get(0).getDescription(), produitList.get(0).getDescription());
        Assert.assertEquals(listDTO.get(0).getNom(), produitList.get(0).getNom());
        Assert.assertEquals(listDTO.get(0).getPhotos().get(0).getId(), produitList.get(0).getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(listDTO.get(0).getPhotos().get(0).getUrl(), produitList.get(0).getPhotos().get(0).getUrl());
        Assert.assertEquals(listDTO.get(0).getPrixHT(), produitList.get(0).getPrixHT(), 0);
        Assert.assertEquals(listDTO.get(0).getRef(), produitList.get(0).getReferenceProduit());
    }

    //Builders

    public Produit buildProduitEntity() {
        Produit produit = new Produit();
        produit.setDescription("ceci est une description du produit");
        produit.setNom("Produit_nom");
        produit.setPrixHT(1);
        produit.setReferenceProduit("A4224");
        //Set category list
        produit.setCategories(new ArrayList<>());
        List<Categorie> categorieList = new ArrayList<>();
        List<Produit> produitList = new ArrayList<>();
        produitList.add(produit);
        categorieList.add(buildCategorieEntity(produitList));
        produit.setCategories(categorieList);
        //Set photo list
        produit.setPhotos(new ArrayList<>());
        List<Photo> photoList = new ArrayList<>();
        photoList.add(buildPhotoEntity(produit));
        produit.setPhotos(photoList);
        //
        return produit;
    }

    public ProduitDTO buildProduitDTO() {
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setDescription("ceci est une description du produitDTO");
        produitDTO.setNom("Produit_nom");
        produitDTO.setPrixHT(1);
        produitDTO.setRef("A4224");
        produitDTO.setPhotoPrincipale(buildPhotoDto(produitDTO));
        //Set category list
        produitDTO.setCategories(new ArrayList<>());
        List<CategorieDTO> categorieDTOList = new ArrayList<>();
        List<ProduitDTO> produitDTOList = new ArrayList<>();
        produitDTOList.add(produitDTO);
        categorieDTOList.add(buildCategorieDto(produitDTOList));
        produitDTO.setCategories(categorieDTOList);
        //Set photo list
        produitDTO.setPhotos(new ArrayList<>());
        List<PhotoDTO> photoDTOList = new ArrayList<>();
        photoDTOList.add(buildPhotoDto(produitDTO));
        produitDTO.setPhotos(photoDTOList);
        //
        return produitDTO;
    }

    public Categorie buildCategorieEntity(List<Produit> productList) {
        Categorie categorie = new Categorie();
        categorie.setBorneDroit(1);
        categorie.setBorneGauche(0);
        categorie.setNomCategorie("Categorie_nom");
        categorie.setIdCategorie(1);
        categorie.setLevel(1);
        categorie.setProduits(productList);
        return categorie;
    }

    public CategorieDTO buildCategorieDto(List<ProduitDTO> produitDTOList) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setId(1);
        categorieDTO.setNom("Categorie_nom");
        categorieDTO.setLevel(1);
        categorieDTO.setProfondeur(1);
        //
        List<CategorieDTO> categorieDTOCheminList = new ArrayList<>();
        categorieDTO.setChemin(new ArrayList<>());
        CategorieDTO categorieDTOChemin = new CategorieDTO();
        categorieDTOCheminList.add(categorieDTOChemin);
        categorieDTO.setChemin(categorieDTOCheminList);
        //
        List<CategorieDTO> categorieDTOSousCatList = new ArrayList<>();
        categorieDTO.setSousCategories(new ArrayList<>());
        CategorieDTO categorieDTOSousCat = new CategorieDTO();
        categorieDTOSousCatList.add(categorieDTOSousCat);
        categorieDTO.setChemin(categorieDTOCheminList);
        //
        categorieDTO.setParent(new CategorieDTO());
        return categorieDTO;
    }

    public Photo buildPhotoEntity(Produit product) {
        Photo photo = new Photo();
        photo.setIdPhoto(1);
        photo.setNom("Photo_entity");
        photo.setUrl("Photo_entity_url");
        photo.setProduit(product);
        return photo;
    }

    public PhotoDTO buildPhotoDto(ProduitDTO productDto) {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setIdPhoto(1);
        photoDTO.setNom("Photo_dto");
        photoDTO.setUrl("Photo_dto_url");
        return photoDTO;
    }
}