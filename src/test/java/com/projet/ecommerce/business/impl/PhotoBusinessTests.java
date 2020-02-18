package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles(profiles = "test")
public class PhotoBusinessTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoBusinessTests.class);
    private static final String IMG_ROOT = "src/main/resources/img/";

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private PhotoBusiness photoBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(photoBusiness, "repertoireImg", IMG_ROOT);
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
            LOGGER.error("testUploadFichierVide", e);
        }
        String contentType = "image/jpeg";
        MultipartFile mpf = new MockMultipartFile(name, originalFileName, contentType, content);
        //XXX - assert.fail() dans le catch ?
        try {
            Assert.assertFalse(photoBusiness.upload(mpf, "hhh"));
        } catch (Exception e) {
            LOGGER.error("testUploadFichierVide", e);
            Assert.fail();
        }
    }

    /**
     * Cas normal.
     */
    @Test
    public void testUploadNormal() {
        // Création du produit auquel on veut ajouter une image
        Produit p = new Produit();
        p.setReferenceProduit("REF");
        LocalDateTime date = LocalDateTime.now();
        p.setDateAjout(date);
        p.setPrixHT(44);
        p.setNom("NOM PRODUIT");
        List<Photo> arrayPhotos = new ArrayList<>();
        p.setPhotos(arrayPhotos);

        // Construction du fichier
        String name = "file.jpeg";
        String originalFileName = "file.jpeg";
        byte[] content = "Juste des données au pif".getBytes();
        String contentType = "image/jpeg";
        MultipartFile mpf = new MockMultipartFile(name, originalFileName, contentType, content);
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(p));
        // Test du 1ier if
        try {
            Assert.assertTrue(photoBusiness.upload(mpf, "hello"));
        } catch (PhotoException e) {
            LOGGER.error("testUploadNormal", e);
            Assert.fail();
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
        List<Photo> arrayPhotos = new ArrayList<>();
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
        List<Photo> arrayPhotos = new ArrayList<>();
        p.setPhotos(arrayPhotos);

        // Construction du fichier
        String name = "file.jpg";
        String originalFileName = "fileOriginal.jpg";
        String contentType = "image/jpeg";
        byte[] content = "Juste des données au pif".getBytes();
        MultipartFile mpf = new MockMultipartFile(name, originalFileName, contentType, content);

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(p));

        try {
            photoBusiness.upload(mpf, p.getReferenceProduit());
        } catch (PhotoException e) {
            LOGGER.error("constructionNouveauRepertoireDeStockage", e);
            Assert.fail();
        }

        try {
            // vérifier que le dossier a bien été créé
            Assert.assertTrue(Files.isDirectory(Paths.get("src/main/resources/img/1900/1/1/REF")));

            // Vérifier que le fichier a bien été ajouté et qu'il a le mD5 du fichier comme nom
            StringBuilder sbMd5 = new StringBuilder();
            DigestUtils.appendMd5DigestAsHex(content, sbMd5);
            Assert.assertTrue(Files.isRegularFile(Paths.get("src/main/resources/img/1900/1/1/REF/" + sbMd5.toString() + ".jpg")));
        } catch (Exception e) {

        } finally {
            // Suppression du fichier et dossier créé
            forceDeleteProductDirectory(Paths.get("src/main/resources/img/1900/1/1/REF"));
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
        List<Photo> arrayPhotos = new ArrayList<>();
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
            LOGGER.info("1ier ajout");
            photoBusiness.upload(mpf, p.getReferenceProduit());
        } catch (PhotoException e) {
            // Suppression du fichier et dossier créé
            forceDeleteProductDirectory(Paths.get("src/main/resources/img/1900/1/1/REF"));
            LOGGER.error("constructionNouveauRepertoireDeStockage", e);
            Assert.fail();
        }

        // 2eme ajout
        try {
            LOGGER.info("2e ajout");
            photoBusiness.upload(mpf, p.getReferenceProduit());
        } catch (PhotoException e) {
            throw new PhotoException("Test OK");
        } finally {
            // Suppression du fichier et dossier créé
            forceDeleteProductDirectory(Paths.get("src/main/resources/img/1900/1/1/REF"));
        }
    }

    private void forceDeleteProductDirectory(Path productPath) {
        try {
            FileUtils.forceDeleteOnExit(productPath.toFile());
            LOGGER.info("Le dossier créé durant le test a été supprimé.");
        } catch (IOException e) {
            LOGGER.error("Erreur lors de la suppression du dossier de test d'image de produit!", e);
            Assert.fail();
        }
    }
}
