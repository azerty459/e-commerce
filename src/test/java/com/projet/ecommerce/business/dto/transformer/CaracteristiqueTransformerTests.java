package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiquePK;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

@SpringBootTest
public class CaracteristiqueTransformerTests {

	private static final ProduitDTO PRODUIT_DTO;

    private static final Produit PRODUIT;
    
    private static final CategorieDTO CATEGORIE_DTO;
    private static final List<CategorieDTO> LIST_CATEGORIE_DTO;
    
    private static final Categorie CATEGORIE;
    private static final List<Categorie> LIST_CATEGORIE;
    
    private static final PhotoDTO PHOTO_DTO1;
    private static final Photo PHOTO1;
	
	private static final TypeCaracteristiqueDTO TYPE_CARACTERISTIQUE_DTO1;
	private static final TypeCaracteristiqueDTO TYPE_CARACTERISTIQUE_DTO2;
	private static final TypeCaracteristique TYPE_CARACTERISTIQUE_1;
	private static final TypeCaracteristique TYPE_CARACTERISTIQUE_2;
	
	private static final CaracteristiqueDTO CARACTERISTIQUE_DTO1;
	private static final CaracteristiqueDTO CARACTERISTIQUE_DTO2;
	private static final Caracteristique CARACTERISTIQUE_1;
	private static final Caracteristique CARACTERISTIQUE_2;
	
	static {
		CATEGORIE_DTO = new CategorieDTO();
		CATEGORIE_DTO.setNom("catégorie");
		
		LIST_CATEGORIE_DTO = new ArrayList<CategorieDTO>();
		LIST_CATEGORIE_DTO.add(CATEGORIE_DTO);
		
		CATEGORIE = new Categorie();
		CATEGORIE.setNomCategorie("catégorie");
		
		LIST_CATEGORIE = new ArrayList<Categorie>();
		LIST_CATEGORIE.add(CATEGORIE);
		
		PHOTO_DTO1 = new PhotoDTO();
        PHOTO_DTO1.setUrl("test/test1.jpg");
        PHOTO_DTO1.setIdPhoto(1);

        PHOTO1 = new Photo();
        PHOTO1.setUrl("test/test1.jpg");
        PHOTO1.setIdPhoto(1);
		
		PRODUIT_DTO = new ProduitDTO();
        PRODUIT_DTO.setCategories(LIST_CATEGORIE_DTO);
        PRODUIT_DTO.setDescription("ceci est un test");
        PRODUIT_DTO.setNom("test");
        PRODUIT_DTO.setPrixHT(1);
        PRODUIT_DTO.setRef("A4224");
        PRODUIT_DTO.setPhotoPrincipale(PHOTO_DTO1);
        PRODUIT_DTO.setPhotos(new ArrayList<PhotoDTO>());
        
        PRODUIT = new Produit();
        PRODUIT.setCategories(LIST_CATEGORIE);
        PRODUIT.setDescription("ceci est un test");
        PRODUIT.setNom("test");
        PRODUIT.setPrixHT(1);
        PRODUIT.setReferenceProduit("A4224");
        PRODUIT.setPhotoPrincipale(PHOTO1);
        PRODUIT.setPhotos(new ArrayList<Photo>());
		
		TYPE_CARACTERISTIQUE_DTO1 = new TypeCaracteristiqueDTO();
		TYPE_CARACTERISTIQUE_DTO1.setId(1);
		TYPE_CARACTERISTIQUE_DTO1.setNomType("Editeur");
		
		TYPE_CARACTERISTIQUE_DTO2 = new TypeCaracteristiqueDTO();
		TYPE_CARACTERISTIQUE_DTO2.setId(2);
		TYPE_CARACTERISTIQUE_DTO2.setNomType("Langue");
		
		TYPE_CARACTERISTIQUE_1 = new TypeCaracteristique();
		TYPE_CARACTERISTIQUE_1.setIdTypeCaracteristique(1);
		TYPE_CARACTERISTIQUE_1.setNomTypeCaracteristique("Editeur");
		
		TYPE_CARACTERISTIQUE_2 = new TypeCaracteristique();
		TYPE_CARACTERISTIQUE_2.setIdTypeCaracteristique(2);
		TYPE_CARACTERISTIQUE_2.setNomTypeCaracteristique("Langue");
		
		CARACTERISTIQUE_DTO1 = new CaracteristiqueDTO();
		CARACTERISTIQUE_DTO1.setId(1);
		CARACTERISTIQUE_DTO1.setTypeCaracteristiqueDto(TYPE_CARACTERISTIQUE_DTO1);
		CARACTERISTIQUE_DTO1.setValeurCaracteristique("Nathan");
		CARACTERISTIQUE_DTO1.setProduitDto(PRODUIT_DTO);
		
		CARACTERISTIQUE_DTO2 = new CaracteristiqueDTO();
		CARACTERISTIQUE_DTO2.setId(2);
		CARACTERISTIQUE_DTO2.setTypeCaracteristiqueDto(TYPE_CARACTERISTIQUE_DTO2);
		CARACTERISTIQUE_DTO2.setValeurCaracteristique("Français");
		CARACTERISTIQUE_DTO2.setProduitDto(PRODUIT_DTO);
		
		CARACTERISTIQUE_1 = new Caracteristique();
		CARACTERISTIQUE_1.setIdCaracteristique(1);
		CARACTERISTIQUE_1.setValeurCaracteristique("Nathan");
		CARACTERISTIQUE_1.setCaracteristiquePk(new CaracteristiquePK());
		CARACTERISTIQUE_1.getCaracteristiquePk().setTypeCaracteristique(TYPE_CARACTERISTIQUE_1);
		CARACTERISTIQUE_1.getCaracteristiquePk().setProduit(PRODUIT);
		
		CARACTERISTIQUE_2 = new Caracteristique();
		CARACTERISTIQUE_2.setIdCaracteristique(2);
		CARACTERISTIQUE_2.setValeurCaracteristique("Français");
		CARACTERISTIQUE_2.setCaracteristiquePk(new CaracteristiquePK());
		CARACTERISTIQUE_2.getCaracteristiquePk().setTypeCaracteristique(TYPE_CARACTERISTIQUE_2);
		CARACTERISTIQUE_2.getCaracteristiquePk().setProduit(PRODUIT);
	}
	
	@Test
    public void singleDtoToEntity() {
        Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(CARACTERISTIQUE_DTO1);
        assertDataCaracteristique(CARACTERISTIQUE_DTO1, caracteristique);
    }
	
//	@Test
//    public void singleEntityToDto() {
//        TypeCaracteristiqueDTO typeCaracteristiqueDto = TypeCaracteristiqueTransformer.entityToDto(TYPE_CARACTERISTIQUE_1);
//
//        assertData(typeCaracteristiqueDto, TYPE_CARACTERISTIQUE_1);
//    }
	
//	@Test
//    public void ListeDtoToEntity() {
//        List<TypeCaracteristiqueDTO> typeCaracteristiqueDTOList = new ArrayList<>();
//        typeCaracteristiqueDTOList.add(CARACTERISTIQUE_DTO1);
//        typeCaracteristiqueDTOList.add(TYPE_CARACTERISTIQUE_DTO2);
//
//        List<TypeCaracteristique> typeCaracteristiqueList = new ArrayList<>(TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTOList));
//
//        Assert.assertNotNull(typeCaracteristiqueList);
//        this.assertData(typeCaracteristiqueDTOList.get(0), typeCaracteristiqueList.get(0));
//        this.assertData(typeCaracteristiqueDTOList.get(1), typeCaracteristiqueList.get(1));
//    }
	
//	@Test
//    public void ListeEntityToDto() {
//        List<TypeCaracteristique> typeCaracteristiqueList = new ArrayList<>();
//        typeCaracteristiqueList.add(TYPE_CARACTERISTIQUE_1);
//        typeCaracteristiqueList.add(TYPE_CARACTERISTIQUE_2);
//
//        List<TypeCaracteristiqueDTO> typeCaracteristiqueDtoList = new ArrayList<>(TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueList));
//
//        Assert.assertNotNull(typeCaracteristiqueList);
//        this.assertData(typeCaracteristiqueDtoList.get(0), typeCaracteristiqueList.get(0));
//        this.assertData(typeCaracteristiqueDtoList.get(1), typeCaracteristiqueList.get(1));
//    }

	private void assertDataCaracteristique(CaracteristiqueDTO caracteristiqueDto, Caracteristique caracteristique) {
		Assert.assertNotNull(caracteristique);
		Assert.assertNotNull(caracteristiqueDto);
        Assert.assertEquals(caracteristiqueDto.getId(), caracteristique.getIdCaracteristique());
        Assert.assertEquals(caracteristiqueDto.getTypeCaracteristiqueDto().getId(), caracteristique.getCaracteristiquePk().getTypeCaracteristique().getIdTypeCaracteristique());
        Assert.assertEquals(caracteristiqueDto.getTypeCaracteristiqueDto().getNomType(), caracteristique.getCaracteristiquePk().getTypeCaracteristique().getNomTypeCaracteristique());
        assertDataProduit(caracteristiqueDto.getProduitDto(), caracteristique.getCaracteristiquePk().getProduit());
//        Assert.assertEquals(caracteristiqueDto.getProduitDto(), caracteristique.getCaracteristiquePk().getProduit());
        Assert.assertEquals(caracteristiqueDto.getValeurCaracteristique(), caracteristique.getValeurCaracteristique());

	}
	
	private void assertDataProduit(ProduitDTO produitDto, Produit produit) {
        Assert.assertNotNull(produitDto);
        Assert.assertNotNull(produit);
        Assert.assertEquals(produitDto.getCategories().get(0).getNom(), produit.getCategories().get(0).getNomCategorie());
        Assert.assertEquals(produitDto.getDescription(), produit.getDescription());
        Assert.assertEquals(produitDto.getNom(), produit.getNom());
        Assert.assertEquals(produitDto.getPhotos().get(0).getId(), produit.getPhotos().get(0).getIdPhoto());
        Assert.assertEquals(produitDto.getPhotos().get(0).getUrl(), produit.getPhotos().get(0).getUrl());
        Assert.assertEquals(produitDto.getPrixHT(), produit.getPrixHT(), 0);
        Assert.assertEquals(produitDto.getRef(), produit.getReferenceProduit());
	}
}
