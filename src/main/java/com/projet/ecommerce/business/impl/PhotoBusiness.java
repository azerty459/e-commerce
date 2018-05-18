package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IPhotoBusiness;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.transformer.PhotoTransformer;
import com.projet.ecommerce.entrypoint.graphQL.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PhotoBusiness implements IPhotoBusiness {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Methode permettant l'upload d'un fichier
     * @param fichier le fichier a upload
     * @param refProduit ref du produit
     * @return true si réussite, false si echec
     */
    @Override
    public Boolean upload(MultipartFile fichier, String refProduit) {
        String repertoireImg="src/main/resources/img/"; //TODO aller chercher la variable dans le fichier properties
        if (!fichier.isEmpty()) {
            try {
                Optional<Produit> produitOptional = produitRepository.findById(refProduit);
                if (produitOptional.isPresent()){
                    File repertoire=new File(repertoireImg + refProduit); // Le repertoire image spécifique au produit
                    //On vérifie que le repertoire correspondant au produit existe sinon on le créé
                    if(!repertoire.exists() && !repertoire.isDirectory()) {
                        if (!repertoire.mkdir()) {
                            throw new GraphQLCustomException("Le dossier image pour le produit n'a pas pu être créé");
                        } else {
                            System.out.println("Le dossier " + refProduit + " a bien été créé dans le repertoire image");
                        }
                    }
                    // Chemin de l'image a écrire , on ajoute le nombre de fichier au début du nom du fichier pour éviter les doublons

                    Integer nombreDeFichier = Objects.requireNonNull(repertoire.listFiles()).length;
                    Path pathFichier = Paths.get(repertoireImg + refProduit +'/'+Integer.toString(nombreDeFichier)+'-'+fichier.getOriginalFilename());
                    byte[] bytes = fichier.getBytes(); // on va cherche les bits du fichier

                    if(bytes==null){
                        throw new GraphQLCustomException("le fichier est vide");
                    }else {
                        Files.write(pathFichier, bytes); // On les écrits à l'endroit voulu
                    }
                    //On lie la photo et son produit dans la base de donnée
                    Produit produit = produitOptional.get();
                    Photo photo = new Photo();
                    photo.setProduit(produit);
                    photo.setUrl(refProduit+'/'+Integer.toString(nombreDeFichier)+'-'+fichier.getOriginalFilename());
                    List<Photo> photoList = produit.getPhotos();
                    photoList.add(photo);
                    produit.setPhotos(photoList);
                    produitRepository.save(produit);
                    return true;
                }else{
                    throw new GraphQLCustomException("Le produit recherche n'existe pas.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return  false;
    }

    /**
     * Permet de charger un photo d'un produit
     * @param nomFichier Le nom de la photo a aller chercher
     * @param refProduit La reference du produit
     * @return La resource contenant la photo du produit voulu
     */
    @Override
    public Resource loadPhotos(String nomFichier,String refProduit) {
        String repertoireImg="src/main/resources/img/"+refProduit; //TODO aller chercher la variable dans le fichier properties
        Path rootLocation = Paths.get(repertoireImg);
        try {
            Path file = rootLocation.resolve(nomFichier);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("echec du chargement");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("echec du chargement");
        }
    }

    /**
     * Permet d'aller chercher la liste des photo d'un produit
     * @param ref la référence du produit recherché
     * @return la liste de photo d'un produit
     */
    @Override
    public List<PhotoDTO> getAll(String ref) {
        return new ArrayList<>(PhotoTransformer.entityToDto(photoRepository.findAllWithCriteria(ref)));
    }

//    /**
//     *
//     * @return
//     */
//    @Override
//    public List<Photo> getPhoto() {
//        return null;
//    }


//
//    @Override
//    public PhotoDTO addPhoto(String url, Produit produit) {
//       return null;
//    }
//
//    @Override
//    public Photo updatePhoto(int idPhoto, String url, Produit produit) {
//        return null;
//    }
//
//    @Override
//    public boolean deletePhoto(int idPhoto) {
//        return false;
//    }


//    @Override
//    public Photo getPhotoByID(int idPhoto) {
//        return null;
//    }


}
