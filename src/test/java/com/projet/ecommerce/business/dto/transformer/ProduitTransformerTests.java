package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProduitTransformerTests {

    private static final CategorieDTO SOUS_CATEGORIE_DTO;

    private static final CategorieDTO CATEGORIE_DTO;

    private static final CaracteristiqueDTO CARACTERISTIQUE_DTO1;
    private static final CaracteristiqueDTO CARACTERISTIQUE_DTO2;
    private static final List<CaracteristiqueDTO> CARACTERISTIQUE_DTOS = new ArrayList<>();

    private static final ArrayList<CategorieDTO> LIST_CATEGORIE_DTO1;
    private static final ArrayList<CategorieDTO> LIST_CATEGORIE_DTO2;

    private static final ArrayList<Categorie> LISTE_CATEGORIE1;

    private static final PhotoDTO PHOTO_DTO;
    private static final Photo PHOTO;

    private static final ArrayList<Photo> LIST_PHOTO;
    private static final ArrayList<PhotoDTO> LIST_PHOTO_DTO;

    private static final ProduitDTO PRODUIT_DTO1;
    private static final ProduitDTO PRODUIT_DTO2;

    private static final Produit PRODUIT1;
    private static final Produit PRODUIT2;

    private static final List<Produit> LIST_PRODUIT;
    private static final List<ProduitDTO> LIST_PRODUIT_DTO;


    private static final Caracteristique CARACTERISTIQUE_1;
    private static final  Caracteristique CARACTERISTIQUE_2;
    private static final List<Caracteristique> CARACTERISTIQUES = new ArrayList<>();

    static {
        SOUS_CATEGORIE_DTO = new CategorieDTO();
        CATEGORIE_DTO = new CategorieDTO();
        LIST_CATEGORIE_DTO1 = new ArrayList<>();
        LIST_CATEGORIE_DTO2 = new ArrayList<>();
        SOUS_CATEGORIE_DTO.setNom("sous catégorie");
        LIST_CATEGORIE_DTO1.add(SOUS_CATEGORIE_DTO);
        CATEGORIE_DTO.setNom("catégorie");
        CATEGORIE_DTO.setSousCategories(LIST_CATEGORIE_DTO1);
        LIST_CATEGORIE_DTO2.add(CATEGORIE_DTO);

        CARACTERISTIQUE_DTO1 = new CaracteristiqueDTO();
        CARACTERISTIQUE_DTO1.setType("Broché");
        CARACTERISTIQUE_DTO1.setValeur("223 pages");
        CARACTERISTIQUE_DTOS.add(CARACTERISTIQUE_DTO1);

        CARACTERISTIQUE_DTO2 = new CaracteristiqueDTO();
        CARACTERISTIQUE_DTO2.setType("Editeur");
        CARACTERISTIQUE_DTO2.setValeur("Flammarion");
        CARACTERISTIQUE_DTOS.add(CARACTERISTIQUE_DTO2);

        CARACTERISTIQUE_1 = new Caracteristique();
        CARACTERISTIQUE_1.setIdCaracteristique(1);
        CARACTERISTIQUE_1.setType(new TypeCaracteristique(1, "Broché"));
        CARACTERISTIQUE_1.setValeur("223 pages");
        CARACTERISTIQUES.add(CARACTERISTIQUE_1);

        CARACTERISTIQUE_2 = new Caracteristique();
        CARACTERISTIQUE_2.setIdCaracteristique(2);
        CARACTERISTIQUE_2.setType(new TypeCaracteristique(2, "Editeur"));
        CARACTERISTIQUE_2.setValeur("Flammarion");
        CARACTERISTIQUES.add(CARACTERISTIQUE_2);

        LISTE_CATEGORIE1 = new ArrayList<>();

        PHOTO_DTO = new PhotoDTO();
        LIST_PHOTO_DTO = new ArrayList<>();
        PHOTO_DTO.setIdPhoto(1);
        PHOTO_DTO.setUrl("test");
        LIST_PHOTO_DTO.add(PHOTO_DTO);

        PHOTO = new Photo();
        LIST_PHOTO = new ArrayList<>();
        PHOTO.setIdPhoto(1);
        PHOTO.setUrl("test");
        LIST_PHOTO.add(PHOTO);

        PRODUIT_DTO1 = new ProduitDTO();
        PRODUIT_DTO1.setCategories(LIST_CATEGORIE_DTO2);
        PRODUIT_DTO1.setCaracteristiques(CARACTERISTIQUE_DTOS);
        PRODUIT_DTO1.setPhotos(LIST_PHOTO_DTO);
        PRODUIT_DTO1.setDescription("ceci est un test");
        PRODUIT_DTO1.setNom("test");
        PRODUIT_DTO1.setPrixHT(1);
        PRODUIT_DTO1.setRef("A4224");

        PRODUIT_DTO2 = new ProduitDTO();
        PRODUIT_DTO2.setCategories(LIST_CATEGORIE_DTO2);
        PRODUIT_DTO2.setCaracteristiques(CARACTERISTIQUE_DTOS);
        PRODUIT_DTO2.setPhotos(LIST_PHOTO_DTO);
        PRODUIT_DTO2.setDescription("ceci est un test");
        PRODUIT_DTO2.setNom("test");
        PRODUIT_DTO2.setPrixHT(1);
        PRODUIT_DTO2.setRef("A4224");

        PRODUIT1 = new Produit();
        PRODUIT1.setCategories(LISTE_CATEGORIE1);
        PRODUIT1.setPhotos(LIST_PHOTO);
        PRODUIT1.setDescription("ceci est un test");
        PRODUIT1.setNom("test");
        PRODUIT1.setPrixHT(1);
        PRODUIT1.setReferenceProduit("A4224");
        PRODUIT1.setCaracteristiques(CARACTERISTIQUES);

        PRODUIT2 = new Produit();
        PRODUIT2.setCategories(LISTE_CATEGORIE1);
        PRODUIT2.setPhotos(LIST_PHOTO);
        PRODUIT2.setDescription("ceci est un test");
        PRODUIT2.setNom("test");
        PRODUIT2.setPrixHT(1);
        PRODUIT2.setReferenceProduit("A4224");
        PRODUIT2.setCaracteristiques(CARACTERISTIQUES);

        LIST_PRODUIT = new ArrayList<>();
        LIST_PRODUIT.add(PRODUIT1);
        LIST_PRODUIT.add(PRODUIT2);

        LIST_PRODUIT_DTO = new ArrayList<>();
        LIST_PRODUIT_DTO.add(PRODUIT_DTO1);
        LIST_PRODUIT_DTO.add(PRODUIT_DTO2);


    }

    @Test
    public void singleDtoToEntity() {
        Produit prod = ProduitTransformer.dtoToEntity(PRODUIT_DTO1);
        Assert.assertNotNull(prod);
        Assert.assertEquals(PRODUIT_DTO1.getCategories().get(0).getNom(), prod.getCategories().get(0).getNomCategorie());
        Assert.assertEquals(PRODUIT_DTO1.getDescription(), prod.getDescription());
        Assert.assertEquals(PRODUIT_DTO1.getNom(), prod.getNom());
        Assert.assertEquals(PRODUIT_DTO1.getPhotos().get(0).getId(), prod.getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(PRODUIT_DTO1.getPhotos().get(0).getUrl(), prod.getPhotos().get(0).getUrl());
        Assert.assertEquals(PRODUIT_DTO1.getPrixHT(), prod.getPrixHT(), 0);
        Assert.assertEquals(PRODUIT_DTO1.getRef(), prod.getReferenceProduit());
    }

    @Test
    public void singleEntityToDto() {
        ProduitDTO prodDto = ProduitTransformer.entityToDto(PRODUIT1);
        Assert.assertNotNull(prodDto);
        Assert.assertTrue(PRODUIT1.getCategories().containsAll(prodDto.getCategories()));
        Assert.assertTrue(PRODUIT1.getCaracteristiques().get(0).getValeur().equals(prodDto.getCaracteristiques().get(0).getValeur()));
        Assert.assertEquals(PRODUIT1.getDescription(), prodDto.getDescription());
        Assert.assertEquals(PRODUIT1.getNom(), prodDto.getNom());
        Assert.assertEquals(PRODUIT1.getPhotos().get(0).getIdPhoto(), prodDto.getPhotos().get(0).getId());
        Assert.assertEquals(PRODUIT1.getPhotos().get(0).getUrl(), prodDto.getPhotos().get(0).getUrl());
        Assert.assertEquals(PRODUIT1.getPrixHT(), prodDto.getPrixHT(), 0);
        Assert.assertEquals(PRODUIT1.getReferenceProduit(), prodDto.getRef());
    }

    @Test
    public void severalDtoToEntity() {
        List<Produit> listProd = new ArrayList<>(ProduitTransformer.dtoToEntity(LIST_PRODUIT_DTO));

        Assert.assertNotNull(listProd);
        Assert.assertEquals(listProd.get(0).getCategories().get(0).getNomCategorie(), LIST_PRODUIT_DTO.get(0).getCategories().get(0).getNom());
        Assert.assertEquals(listProd.get(0).getCaracteristiques().get(0).getValeur(), LIST_PRODUIT_DTO.get(0).getCaracteristiques().get(0).getValeur());
        Assert.assertEquals(listProd.get(0).getDescription(), LIST_PRODUIT_DTO.get(0).getDescription());
        Assert.assertEquals(listProd.get(0).getNom(), LIST_PRODUIT_DTO.get(0).getNom());
        Assert.assertEquals(listProd.get(0).getPhotos().get(0).getIdPhoto(), LIST_PRODUIT_DTO.get(0).getPhotos().get(0).getId());
        Assert.assertEquals(listProd.get(0).getPhotos().get(0).getUrl(), LIST_PRODUIT_DTO.get(0).getPhotos().get(0).getUrl());
        Assert.assertEquals(listProd.get(0).getPrixHT(), LIST_PRODUIT_DTO.get(0).getPrixHT(), 0);
        Assert.assertEquals(listProd.get(0).getReferenceProduit(), LIST_PRODUIT_DTO.get(0).getRef());
    }

    @Test
    public void severalEntityToDto() {
        List<ProduitDTO> listDTO = new ArrayList<>(ProduitTransformer.entityToDto(LIST_PRODUIT));

        Assert.assertNotNull(listDTO);
        Assert.assertEquals(listDTO.get(0).getCategories(), LIST_PRODUIT.get(0).getCategories());
        Assert.assertEquals(listDTO.get(0).getCaracteristiques().get(0).getValeur(), LIST_PRODUIT.get(0).getCaracteristiques().get(0).getValeur());
        Assert.assertEquals(listDTO.get(0).getCaracteristiques().get(0).getType(), LIST_PRODUIT.get(0).getCaracteristiques().get(0).getType().getType());
        Assert.assertEquals(listDTO.get(0).getDescription(), LIST_PRODUIT.get(0).getDescription());
        Assert.assertEquals(listDTO.get(0).getNom(), LIST_PRODUIT.get(0).getNom());
        Assert.assertEquals(listDTO.get(0).getPhotos().get(0).getId(), LIST_PRODUIT.get(0).getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(listDTO.get(0).getPhotos().get(0).getUrl(), LIST_PRODUIT.get(0).getPhotos().get(0).getUrl());
        Assert.assertEquals(listDTO.get(0).getPrixHT(), LIST_PRODUIT.get(0).getPrixHT(), 0);
        Assert.assertEquals(listDTO.get(0).getRef(), LIST_PRODUIT.get(0).getReferenceProduit());
    }
}