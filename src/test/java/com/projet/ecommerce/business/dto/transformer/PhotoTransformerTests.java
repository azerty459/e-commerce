package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.persistance.entity.Photo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PhotoTransformerTests {

    private static final PhotoDTO PHOTO_DTO1;
    private static final PhotoDTO PHOTO_DTO2;
    private static final Photo PHOTO1;
    private static final Photo PHOTO2;

    static {
        PHOTO_DTO1 = new PhotoDTO();
        PHOTO_DTO1.setUrl("test/test1.jpg");
        PHOTO_DTO1.setIdPhoto(1);

        PHOTO_DTO2 = new PhotoDTO();
        PHOTO_DTO2.setUrl("test/test2.jpg");
        PHOTO_DTO2.setIdPhoto(2);

        PHOTO1 = new Photo();
        PHOTO1.setUrl("test/test1.jpg");
        PHOTO1.setIdPhoto(1);

        PHOTO2 = new Photo();
        PHOTO2.setUrl("test/test2.jpg");
        PHOTO2.setIdPhoto(2);
    }

    @Test
    public void singleDtoToEntity() {
        Photo photo = PhotoTransformer.dtoToEntity(PHOTO_DTO1);

        Assert.assertNotNull(photo);
        Assert.assertEquals(PHOTO_DTO1.getUrl(), photo.getUrl());
        Assert.assertEquals(PHOTO_DTO1.getId(), photo.getIdPhoto());
    }

    @Test
    public void singleEntityToDto() {
        PhotoDTO photoDTO = PhotoTransformer.entityToDto(PHOTO1);

        Assert.assertNotNull(photoDTO);
        Assert.assertEquals(photoDTO.getUrl(), PHOTO1.getUrl());
        Assert.assertEquals(photoDTO.getId(), PHOTO1.getIdPhoto());
    }

    @Test
    public void severalDtoToEntity() {
        List<PhotoDTO> photoDTOList = new ArrayList<>();
        photoDTOList.add(PHOTO_DTO1);
        photoDTOList.add(PHOTO_DTO2);

        List<Photo> photoList = new ArrayList<>(PhotoTransformer.dtoToEntity(photoDTOList));

        Assert.assertNotNull(photoList);
        Assert.assertEquals(photoList.get(0).getUrl(), photoDTOList.get(0).getUrl());
        Assert.assertEquals(photoList.get(0).getIdPhoto(), photoDTOList.get(0).getId());
        Assert.assertEquals(photoList.get(1).getUrl(), photoDTOList.get(1).getUrl());
        Assert.assertEquals(photoList.get(1).getIdPhoto(), photoDTOList.get(1).getId());
    }

    @Test
    public void severalEntityToDto() {
        List<Photo> photoList = new ArrayList<>();
        photoList.add(PHOTO1);
        photoList.add(PHOTO2);

        List<PhotoDTO> photoDTOList = new ArrayList<>(PhotoTransformer.entityToDto(photoList));

        Assert.assertNotNull(photoDTOList);
        Assert.assertEquals(photoDTOList.get(0).getUrl(), photoList.get(0).getUrl());
        Assert.assertEquals(photoDTOList.get(0).getId(), photoList.get(0).getIdPhoto());
        Assert.assertEquals(photoDTOList.get(1).getUrl(), photoList.get(1).getUrl());
        Assert.assertEquals(photoDTOList.get(1).getId(), photoList.get(1).getIdPhoto());
    }
}