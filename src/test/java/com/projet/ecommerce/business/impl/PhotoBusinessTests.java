package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PhotoBusinessTests {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private PhotoBusiness photoBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void uploadFile() {
        Produit produit = new Produit();
        ArrayList<Photo> photos = new ArrayList<>();
        ArrayList<Caracteristique> caracteristiques = new ArrayList<>();
        ArrayList<Categorie> categories = new ArrayList<>();
        produit.setPhotos(photos);
        produit.setDescription("description");
        produit.setPrixHT(5.2f);
        produit.setReferenceProduit("test");
        produit.setCategories(categories);
        produit.setCaracteristiques(caracteristiques);
        produit.setNom("test");
        byte[] b = new byte[4];
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
        try {
            Mockito.when(multipartFile.getBytes()).thenThrow(new FileNotFoundException());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertTrue(photoBusiness.upload(multipartFile, "test"));
        try {
            Files.delete(Paths.get("src/main/resources/img/test/0-null"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(Paths.get("src/main/resources/img/test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadFileEmptyTest() {
        Mockito.when(multipartFile.isEmpty()).thenReturn(true);
        Assert.assertFalse(photoBusiness.upload(multipartFile, null));
    }


    @Test
    public void uploadProduitVide() {
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        thrown.expect(GraphQLCustomException.class);
        photoBusiness.upload(multipartFile, null);
    }


    @Test
    public void uploadFichierVide() {
        // Initialisation
        Produit produit = new Produit();
        ArrayList<Photo> photos = new ArrayList<>();
        ArrayList<Caracteristique> caracteristiques = new ArrayList<>();
        ArrayList<Categorie> categories = new ArrayList<>();
        produit.setPhotos(photos);
        produit.setDescription("description");
        produit.setPrixHT(5.2f);
        produit.setReferenceProduit("A06A02");
        produit.setCategories(categories);
        produit.setCaracteristiques(caracteristiques);
        produit.setNom("test");

        // Test
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
        try {
            Mockito.when(multipartFile.getBytes()).thenReturn(new byte[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        thrown.expect(GraphQLCustomException.class);
        photoBusiness.upload(multipartFile, produit.getReferenceProduit());

    }

    @Test
    //TODO faire le test de loadPhoto
    public void loadPhotoFichier() {

    }

    @Test
    public void loadPhotoFichierInexistant() {
        thrown.expect(RuntimeException.class);
        photoBusiness.loadPhotos("eaz", "A05A02");
    }

    //TODO Refaire le test getAll
//    @Test
//    public void getAll() {
//        Mockito.when(photoRepository.findAllWithCriteria(Mockito.anyString())).thenReturn(new ArrayList<>());
//        List<PhotoDTO> photoDTOList = photoBusiness.getAll("Test");
//        Assert.assertNotNull(photoDTOList);
//    }
}
