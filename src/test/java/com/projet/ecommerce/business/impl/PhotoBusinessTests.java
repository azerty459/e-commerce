package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
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


    /**
     * cas où le fichier uploadé est vide.
     */
    @Test
    public void testUploadFichierVide() {

        // Construction du fichier (test du premier if)
        Path path = Paths.get("path/qui/nexiste/pas");
        String name = "file.jpeg";
        String originalFileName = "file.jpeg";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {

        }
        String contentType = "image/jpeg";
        MultipartFile mpf = new MockMultipartFile(name, originalFileName, contentType, content);

        try {
            Assert.assertFalse(photoBusiness.upload(mpf, "hhh"));
        } catch (Exception e) {

        }
    }

    /**
     * Cas normal.
     */
    @Test
    public void testUploadNormal() {

        // Construction du fichier
        String name = "file.jpeg";
        String originalFileName = "file.jpeg";
        byte[] content = "Juste des données au pif".getBytes();
        String contentType = "image/jpeg";
        MultipartFile mpf = new MockMultipartFile(name, originalFileName, contentType, content);

        // Test du 1ier if
        try {
            Assert.assertTrue(photoBusiness.upload(mpf, "hello"));
        } catch (Exception e) {

        }
    }


    /**
     * Cas où le fichier n'a pas d'extension
     *
     * @throws PhotoException si le fichier n'a pas d'extension
     */
    @Test(expected = PhotoException.class)
    public void verificationFichierNomVideSansExtension() throws PhotoException {

        // Création du produit auquel on veut ajouter une image
        Produit p = new Produit();
        p.setReferenceProduit("REF");
        LocalDateTime date = LocalDateTime.now();
        p.setDateAjout(date);
        p.setPrixHT(44);
        p.setNom("NOM PRODUIT");
        List<Photo> arrayPhotos = new ArrayList<Photo>();
        p.setPhotos(arrayPhotos);


        // Construction du fichier
        String name = "a";
        String originalFileName = "a";
        byte[] content = "Juste des données au pif".getBytes();
        String contentType = "image/jpeg";
        MultipartFile mpf = new MockMultipartFile(name, originalFileName, contentType, content);

        // Test: doit retourner une exception car le nom du fichier n'a pas d'extension
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(p));
        photoBusiness.upload(mpf, "hello");

    }

    /**
     * Test de la construction d'un nouveau répertoire de stockage du fichier.
     */
    @Test
    public void constructionNouveauRepertoireDeStockage() {

        // Création du produit auquel on veut ajouter une image
        Produit p = new Produit();
        p.setReferenceProduit("REF");
        LocalDateTime date = LocalDateTime.of(1900, Month.JANUARY, 1, 1, 1);
        p.setDateAjout(date);
        p.setPrixHT(44);
        p.setNom("NOM PRODUIT");
        List<Photo> arrayPhotos = new ArrayList<Photo>();
        p.setPhotos(arrayPhotos);

        // Construction du fichier
        String name = "file.jpg";
        String originalFileName = "file.jpg";
        byte[] content = "Juste des données au pif".getBytes();
        String contentType = "image/jpeg";
        MultipartFile mpf = new MockMultipartFile(name, originalFileName, contentType, content);

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(p));

        try {
            photoBusiness.upload(mpf, p.getReferenceProduit());
        } catch (PhotoException e) {

        }

        try {
            // vérifier que le dossier a bien été créé
            Assert.assertTrue(Files.isDirectory(Paths.get("src/main/resources/img/1900/1/1/REF")));

            // Vérifier que le fichier a bien été ajouté et qu'il a le mD5 du fichier comme nom
            StringBuilder sb = new StringBuilder();
            DigestUtils.appendMd5DigestAsHex(content, sb);
            Assert.assertTrue(Files.isRegularFile(Paths.get("src/main/resources/img/1900/1/1/REF/" + sb.toString() + ".jpg")));
        } catch (Exception e) {

        } finally {
            // Suppression du fichier et dossier créé
            try {

                FileUtils.deleteDirectory(new File("src/main/resources/img/1900/"));
                System.out.println("Le dossier créé durant le test a été supprimé.");
            } catch (IOException e) {
                System.out.println("Le dossier créé durant le test  n'a pas été supprimé.");
            }
        }
    }


    /**
     * Tester l'upload d'une photo en double
     *
     * @throws PhotoException si le fichier a été ajouté en double
     */
    @Test(expected = PhotoException.class)
    public void uploadPhotoEnDouble() throws PhotoException {

        // Création du produit auquel on veut ajouter une image
        Produit p = new Produit();
        p.setReferenceProduit("REF");
        LocalDateTime date = LocalDateTime.of(1900, Month.JANUARY, 1, 1, 1);
        p.setDateAjout(date);
        p.setPrixHT(44);
        p.setNom("NOM PRODUIT");
        List<Photo> arrayPhotos = new ArrayList<Photo>();
        p.setPhotos(arrayPhotos);

        // Construction du fichier
        String name = "file.jpg";
        String originalFileName = "file.jpg";
        byte[] content = "Juste des données au pif".getBytes();
        String contentType = "image/jpeg";
        MultipartFile mpf = new MockMultipartFile(name, originalFileName, contentType, content);

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(p));

        // 1ier ajout
        try {
            System.out.println("1ier ajout");
            photoBusiness.upload(mpf, p.getReferenceProduit());
        } catch (PhotoException e) {

        }

        // 2eme ajout
        try {
            System.out.println("2e ajout");
            photoBusiness.upload(mpf, p.getReferenceProduit());
        } catch (PhotoException e) {
            throw new PhotoException("Test OK");
        } finally {
            // Suppression du fichier et dossier créé
            try {
                FileUtils.deleteDirectory(new File("src/main/resources/img/1900/"));
                System.out.println("Le dossier créé durant le test a été supprimé.");
            } catch (IOException e) {
                System.out.println("Le dossier créé durant le test  n'a pas été supprimé.");
            }
        }
    }


//    @Test
//    public void loadPhotoFichierInexistant() {
//        thrown.expect(RuntimeException.class);
//        photoBusiness.loadPhotos("eaz", "A05A02");
//    }

    //TODO Refaire le test getAll
//    @Test
//    public void getAll() {
//        Mockito.when(photoRepository.findAllWithCriteria(Mockito.anyString())).thenReturn(new ArrayList<>());
//        List<PhotoDTO> photoDTOList = photoBusiness.getAll("Test");
//        Assert.assertNotNull(photoDTOList);
//    }
}
