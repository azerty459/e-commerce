package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.persistance.entity.Photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PhotoTransformer {

    private PhotoTransformer() {
    }

    /**
     * Transforme une collection d'objets PhotoDTO en une collection d'objets Photo.
     *
     * @param photoDTOCollection Une collection d'objets PhotoDTO
     * @return une collection d'objets Photo
     */
    public static Collection<Photo> dtoToEntity(Collection<PhotoDTO> photoDTOCollection) {
        List<Photo> photoList = new ArrayList<>();
        for (PhotoDTO photoDTO : photoDTOCollection) {
            photoList.add(dtoToEntity(photoDTO));
        }
        return photoList;
    }

    /**
     * Transforme un objet PhotoDTO en un objet Photo.
     *
     * @param photoDTO Un objet photoDTO
     * @return un objet Photo
     */
    public static Photo dtoToEntity(PhotoDTO photoDTO) {
        Photo photo = new Photo();
        if (photoDTO != null) {
            photo.setUrl(photoDTO.getUrl());
            photo.setIdPhoto(photoDTO.getId());
            photo.setNom(photoDTO.getNom());
        }
        return photo;
    }

    /**
     * Transforme une collection d'objets Photo en une collection d'objets PhotoDTO.
     *
     * @param photoCollection Une collection d'objets Photo
     * @return une collection d'objets PhotoDTO
     */
    public static Collection<PhotoDTO> entityToDto(Collection<Photo> photoCollection) {
        List<PhotoDTO> photoDTOList = new ArrayList<>();
        for (Photo photo : photoCollection) {
            if (photo.getIdPhoto() != 0) {
                photoDTOList.add(entityToDto(photo));
            }
        }
        return photoDTOList;
    }

    /**
     * Transforme un objet Photo en PhotoDTO.
     *
     * @param photo Un objet Photo
     * @return un objet PhotoDTO
     */
    public static PhotoDTO entityToDto(Photo photo) {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setIdPhoto(photo.getIdPhoto());
        photoDTO.setUrl(photo.getUrl());
        photoDTO.setNom(photo.getNom());
        return photoDTO;
    }
}
