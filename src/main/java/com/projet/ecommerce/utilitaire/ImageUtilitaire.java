package com.projet.ecommerce.utilitaire;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.entrypoint.image.DimensionImage;


public class ImageUtilitaire {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtilitaire.class);

    private int IMG_WIDTH = 0;
    private int IMG_HEIGHT = 0;
    // taille s
    private int SEUIL_HEIGHT_S = 180;
    private int SEUIL_WIDTH_S = 240;
    // taille m
    private int SEUIL_HEIGHT_M = 375;
    private int SEUIL_WIDTH_M = 500;
    // taille l
    private int SEUIL_HEIGHT_L = 768;
    private int SEUIL_WIDTH_L = 1024;

    /**
     * Methode permettant d'obtenir l'image redimensionné correctement
     *
     * @param dimension  les dimensions demandé
     * @param chemin     le repertoire où est stocké l'image
     * @param nomFichier le nom du fichier de l'image
     * @return la ressource contenant l'image
     */
    public Resource getImage(DimensionImage dimension, String chemin, String nomFichier) {
        Path rootLocation = Paths.get(chemin);
        Path pathFile = rootLocation.resolve(nomFichier);
        InputStream in = null;
        //TODO Voir comment gérer proprement plusieurs try / catch
        try {
            in = new ByteArrayInputStream(Files.readAllBytes(pathFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // On va chercher les dimensions réels
        DimensionImage dimensionImageReel = new DimensionImage();
        if (originalImage == null) {
            dimensionImageReel.setSize(0, 0);
        } else {
            dimensionImageReel.setSize(originalImage.getWidth(), originalImage.getHeight());
        }

        DimensionImage dimensionAdapte = findDimension(dimension, dimensionImageReel);
        if (dimensionAdapte == null) {
            dimensionAdapte = new DimensionImage();
            dimensionAdapte.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        String nomFichierWithSize = Integer.toString(dimensionAdapte.width) + 'x' + Integer.toString(dimensionAdapte.height) + '_' + nomFichier;
        Path fileResized = rootLocation.resolve(nomFichierWithSize);


        if (dimensionImageReel.getHeight() * dimensionImageReel.getWidth() < dimensionAdapte.height * dimensionAdapte.width) {
            try {
                return new UrlResource(pathFile.toUri());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        // TODO else ???

        Resource resource = null;
        try {
            resource = new UrlResource(fileResized.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // On vérifie que le fichier redimensionné n'existe pas
        if (resource != null && (resource.exists() || resource.isReadable())) {
            return resource;
        } else {
            // On créer le fichier contenant la photo redimensionné
            resize(dimensionAdapte, chemin, nomFichier);
            if (resource != null && (resource.exists() || resource.isReadable())) {
                return resource;
            } else {
                throw new GraphQLCustomException("Image introuvable");
            }
        }
    }

    /**
     * Permet de gérer le redimensionnement d'une image
     *
     * @param dimensionImage les dimensions de l'image
     * @param chemin         le chemin du repertoire où est stocké l'image
     * @param nomFichier     le nom du fichier original(non redimensionné) de l'image
     */
    public void resize(DimensionImage dimensionImage, String chemin, String nomFichier) {

        try {
            IMG_HEIGHT = dimensionImage.height;
            IMG_WIDTH = dimensionImage.width;
            BufferedImage originalImage = ImageIO.read(new File(chemin + nomFichier));
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImagePng = resizeImageWithHint(originalImage, type);
            ImageIO.write(resizeImagePng, "png", new File(chemin + dimensionImage.width + 'x' + dimensionImage.height + '_' + nomFichier));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }

    /**
     * Permet de redimensionner l'image sans hint
     *
     * @param originalImage l'image a redimenssionné
     * @param type          le type de l'image
     * @return l'image
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    /**
     * Permet de redimensionner l'image avec un hint
     *
     * @param originalImage l'image a redimenssionné
     * @param type          le type de l'image
     * @return l'image
     */
    private BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();


        return resizedImage;
    }


    /**
     * Methode permettant de trouver les dimensions adaptées
     *
     * @param dimensionImageDemande les dimensions demande
     * @param dimensionImage        les dimensions de l'image
     * @return les dimensions adaptées
     */
    private DimensionImage findDimension(DimensionImage dimensionImageDemande, DimensionImage dimensionImage) {
        double intervalleS = (Math.abs((dimensionImageDemande.getHeight() - SEUIL_HEIGHT_S) * (dimensionImageDemande.getWidth() - SEUIL_WIDTH_S)));
        double intervalleM = (Math.abs((dimensionImageDemande.getHeight() - SEUIL_HEIGHT_M) * (dimensionImageDemande.getWidth() - SEUIL_WIDTH_M)));
        double intervalleL = (Math.abs((dimensionImage.getHeight() - SEUIL_HEIGHT_L) * (dimensionImage.getWidth() - SEUIL_WIDTH_L)));
        if (intervalleS < intervalleM && intervalleS < intervalleL) {
            return getDimensionImage(dimensionImageDemande, dimensionImage, SEUIL_HEIGHT_S, SEUIL_WIDTH_S, SEUIL_WIDTH_M);
        } else if (intervalleM < intervalleS && intervalleM < intervalleL) {
            return getDimensionImage(dimensionImageDemande, dimensionImage, SEUIL_HEIGHT_M, SEUIL_WIDTH_M, SEUIL_WIDTH_L);

        } else if (intervalleL < intervalleS && intervalleL < intervalleM) {
            DimensionImage dimensionL = new DimensionImage();
            dimensionL.setSize(SEUIL_WIDTH_L, SEUIL_WIDTH_L * (dimensionImage.getHeight() / dimensionImage.getWidth()));
            return dimensionL;
        }
        return null;
    }

    private DimensionImage getDimensionImage(DimensionImage dimensionImageDemande, DimensionImage dimensionImage, int seuil_height, int seuil_width, int seuil_width_sup) {
        if (dimensionImageDemande.getHeight() < seuil_height && dimensionImageDemande.getWidth() < seuil_width) {
            DimensionImage dimensionS = new DimensionImage();
            dimensionS.setSize(seuil_width, seuil_width * (dimensionImage.getHeight() / dimensionImage.getWidth()));
            return dimensionS;
        } else {
            DimensionImage dimensionM = new DimensionImage();
            dimensionM.setSize(seuil_width_sup, seuil_width_sup * (dimensionImage.getHeight() / dimensionImage.getWidth()));
            return dimensionM;
        }
    }
}
