package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.persistance.entity.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoTransformer {

    public static List<Photo> dtoToEntity(List<PhotoDTO> photoDTOCollection){
        List<Photo> photoCollection = new ArrayList<>();
        for (PhotoDTO photoDTO : photoDTOCollection) {
            photoCollection.add(dtoToEntity(photoDTO));
        }
        return photoCollection;
    }

    public static Photo dtoToEntity(PhotoDTO photoDTO){
        Photo photo = new Photo();
        photo.setUrl(photoDTO.getUrl());
        photo.setIdPhoto(photoDTO.getIdPhoto());
        return photo;
    }

    public static List<PhotoDTO> entityToDto(List<Photo> photoCollection){
        List<PhotoDTO> photoDTOCollection = new ArrayList<>();
        for (Photo photo : photoCollection) {
            photoDTOCollection.add(entityToDto(photo));
        }
        return photoDTOCollection;
    }

    public static PhotoDTO entityToDto(Photo photo){
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setIdPhoto(photo.getIdPhoto());
        photoDTO.setUrl(photo.getUrl());
        return photoDTO;
    }
}
