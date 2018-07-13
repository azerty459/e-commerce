package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IPhotoBusiness;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.transformer.PhotoTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.entrypoint.image.DimensionImage;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.utilitaire.ImageUtilitaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoBusiness implements IPhotoBusiness {

    private ImageUtilitaire imageUtilitaire = new ImageUtilitaire();
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ProduitRepository produitRepository;

    //TODO aller chercher la variable dans le fichier properties
    /**
     * Le répertoire de base pour les images.
     */
    private static final String repertoireImg = "src/main/resources/img/";

    /**
     * Methode permettant l'upload d'un fichier
     *
     * @param fichier    le fichier a upload
     * @param refProduit ref du produit
     * @return true si réussite, false si echec: fichier vide ou photo déjà présente
     */
    @Override
    public Boolean upload(MultipartFile fichier, String refProduit) throws PhotoException {

        if (fichier.isEmpty()) {
            return false;
        }

        // Nom unique du fichier (MD5)
        String nouveauNomFichier = null;

        // Nom du fichier uploadé (nom originel)
        String nomFichierAffiche = fichier.getOriginalFilename();

        // Extension du fichier originel
        String[] decoupageNom = nomFichierAffiche.split("\\.");
        if (decoupageNom.length < 2) {
            throw new PhotoException("Le fichier n'est pas conforme.");
        }
        String extensionFichier = "." + decoupageNom[decoupageNom.length - 1];


        /*   Construction du répertoire dans lequel le fichier image va être stocké   */

        // Récupérer la date d'insertion du produit.
        Produit produit = recupereProduit(refProduit);
        LocalDateTime dt = produit.getDateAjout();
        int annee = dt.getYear();
        int mois = dt.getMonthValue();
        int jour = dt.getDayOfMonth();

        // Construire le dossier de sauvegarde du fichier, pour toutes les images associées au produit
        String repertoireImgAvecDate = repertoireImg + annee + "/" + mois + "/" + jour + "/";
        File repertoire = new File(repertoireImgAvecDate + refProduit);

        //On vérifie que le repertoire correspondant au produit existe, sinon on le créé
        if (!repertoire.exists() || !repertoire.isDirectory()) {
            if (!repertoire.mkdirs()) {
                throw new GraphQLCustomException("Le dossier image pour le produit n'a pas pu être créé");
            } else {
                System.out.println("Le dossier " + repertoire.getPath().split("resources/img/")[1] + " a bien été créé.");
            }
        }

        // Lecture et sauvegarde de la photo pour le produit
        byte[] bytes = null;
        try {
            bytes = fichier.getBytes();
            if (bytes.length == 0) {
                throw new PhotoException("le fichier est vide");
            } else {

                // Construction du nom du fichier
                StringBuilder sb = new StringBuilder();
                DigestUtils.appendMd5DigestAsHex(bytes, sb);
                nouveauNomFichier = sb.toString() + extensionFichier;

                Path pathFichier = Paths.get(repertoireImgAvecDate + refProduit + '/' + nouveauNomFichier);

                // Sauvegarde du fichier
                Files.write(pathFichier, bytes);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //On lie la photo et son produit dans la base de donnée
        Photo photo = new Photo();
        photo.setProduit(produit);
        photo.setUrl(refProduit + "/" + nouveauNomFichier);
        photo.setNom(nomFichierAffiche);


        // Vérifier que la photo n'est as déjà présente (URL identique)
        List<Photo> photoList = produit.getPhotos();
        for (Photo p : photoList) {
            if (p.aMemeUrl(photo)) {
                throw new PhotoException("Cette photo a déjà été uploadée.");
            }
        }

        // Sauvegarder les photosq
        photoList.add(photo);
        produit.setPhotos(photoList);
        produitRepository.save(produit);
        return true;
    }

    private Produit recupereProduit(String refProduit) {

        // Récupération du produit
        Optional<Produit> produitOptional = produitRepository.findById(refProduit);
        if (!produitOptional.isPresent()) {
            throw new GraphQLCustomException("Le produit recherche n'existe pas.");
        }

        // Récupérer la date d'insertion du produit.
        return produitOptional.get();

    }


    /**
     * Permet de charger une photo d'un produit
     *
     * @param nomFichier Le nom de la photo a aller chercher
     * @param refProduit La reference du produit
     * @return La resource contenant la photo du produit voulu
     */
    @Override
    public Resource loadPhotos(String nomFichier, String refProduit, DimensionImage dimension) {

        // Récupérer le produit
        Produit p = recupereProduit(refProduit);

        // Construire le nom du dossier dans lequel le fichier est
        String dossierImage = repertoireImg + p.getDateAjout().getYear() + "/" + p.getDateAjout().getMonthValue() +
                "/" + p.getDateAjout().getDayOfMonth() + "/" + p.getReferenceProduit() + "/";

        return imageUtilitaire.getImage(dimension, dossierImage, nomFichier);
    }

    /**
     * Permet d'aller chercher la liste des photo d'un produit
     *
     * @param ref la référence du produit recherché
     * @return la liste de photo d'un produit
     */
    @Override
    public List<PhotoDTO> getAll(String ref) {

        System.out.println("getAll pour les photos");
        Collection<Photo> photoCollection = new ArrayList<>();
        if (ref == null) {
            photoCollection.addAll(photoRepository.findAll());
        } else {
            photoCollection.addAll(photoRepository.findByProduit_ReferenceProduit(ref));
        }
        return new ArrayList<>(PhotoTransformer.entityToDto(photoCollection));
    }

    @Override
    public Boolean remove(int idPhoto) {
        if (idPhoto == 0) {
            return false;
        }
        Optional<Photo> photoOptional = photoRepository.findById(idPhoto);
        if (!photoOptional.isPresent()) {
            throw new GraphQLCustomException("Photo introuvable");
        }
        Photo photo = photoOptional.get();
        Produit produit = photo.getProduit();
        boolean supressionReussite = deleteFilePhoto(photo);
        if (supressionReussite) {
            if (produit.getPhotoPrincipale() != null && produit.getPhotoPrincipale().getIdPhoto() == photo.getIdPhoto()) {
                produit.setPhotoPrincipale(null);
                produitRepository.save(produit);
            }
            photoRepository.deleteById(idPhoto);
            return true;
        } else {
            return false;
        }
    }

    private boolean deleteFilePhoto(Photo photo) {
        Produit produit = photo.getProduit();
        String dossierImage = repertoireImg + produit.getDateAjout().getYear() + "/" + produit.getDateAjout().getMonthValue() +
                "/" + produit.getDateAjout().getDayOfMonth() + "/";
        File file = new File(dossierImage + photo.getUrl());
        return file.delete();
    }
}
