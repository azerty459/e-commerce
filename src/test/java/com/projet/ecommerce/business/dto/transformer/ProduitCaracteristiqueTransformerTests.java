package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristique;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristiqueId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProduitCaracteristiqueTransformerTests {
    
    private static final Random RANDOM = new Random();
    private static final int ITERATION = 8;
    
    @Test
    public void testEntityToDto() {
        ProduitCaracteristique produitCaracteristique = buildProduitCaracteristique();
        ProduitCaracteristiqueDTO produitCaracteristiqueDTO = ProduitCaracteristiqueTransformer.entityToDto(produitCaracteristique);
        assertTransformEquals(produitCaracteristique, produitCaracteristiqueDTO);
    }
    
    @Test
    public void testCollectionEntityToDto() {
        List<ProduitCaracteristique> listProduitCaracteristique = new ArrayList<>();
        for(int i = 0; i < ITERATION; i++) {
            listProduitCaracteristique.add(buildProduitCaracteristique());
        }
        List<ProduitCaracteristiqueDTO> listProduitCaracteristiqueDTO = new ArrayList<>(ProduitCaracteristiqueTransformer.entityToDto(listProduitCaracteristique));
        Assert.assertEquals(listProduitCaracteristique.size(), listProduitCaracteristiqueDTO.size());
        for(int i = 0; i < ITERATION; i++) {
            assertTransformEquals(listProduitCaracteristique.get(i), listProduitCaracteristiqueDTO.get(i));
        }
    }
    
    @Test
    public void testDtoToEntity() {
        ProduitCaracteristiqueDTO produitCaracteristiqueDTO = buildProduitCaracteristiqueDTO();
        ProduitCaracteristique produitCaracteristique = ProduitCaracteristiqueTransformer.dtoToEntity(produitCaracteristiqueDTO);
        assertTransformEquals(produitCaracteristique, produitCaracteristiqueDTO);
    }
    
    @Test
    public void testCollectionDtoToEntity() {
        List<ProduitCaracteristiqueDTO> listProduitCaracteristiqueDTO = new ArrayList<>();
        for(int i = 0; i < ITERATION; i++) {
            listProduitCaracteristiqueDTO.add(buildProduitCaracteristiqueDTO());
        }
        List<ProduitCaracteristique> listProduitCaracteristique = new ArrayList<>(ProduitCaracteristiqueTransformer.dtoToEntity(listProduitCaracteristiqueDTO));
        Assert.assertEquals(listProduitCaracteristiqueDTO.size(), listProduitCaracteristique.size());
        for(int i = 0; i < ITERATION; i++) {
            assertTransformEquals(listProduitCaracteristique.get(i), listProduitCaracteristiqueDTO.get(i));
        }
    }
    
    private void assertTransformEquals (ProduitCaracteristique produitCaracteristique, ProduitCaracteristiqueDTO produitCaracteristiqueDTO) {
        Assert.assertEquals(produitCaracteristique.getId().getIdCaracteristique(), produitCaracteristiqueDTO.getCaracteristique().getId());
        Assert.assertEquals(produitCaracteristique.getCaracteristique().getIdCaracteristique(), produitCaracteristiqueDTO.getCaracteristique().getId());
        Assert.assertEquals(produitCaracteristique.getValeur(), produitCaracteristiqueDTO.getValeur());
    }
    
    /* ----- Builder ----- */
    
    private ProduitCaracteristique buildProduitCaracteristique() {
        String ref = randomString();
        int id = RANDOM.nextInt();
        //Creation caracteristique
        Caracteristique c = new Caracteristique();
        c.setIdCaracteristique(id);
        c.setLibelle(randomString());
        //Creation produit
        Produit p = new Produit();
        p.setReferenceProduit(ref);
        //Creation produit caracteristique
        ProduitCaracteristiqueId pcid = new ProduitCaracteristiqueId(ref, id);
        ProduitCaracteristique pc = new ProduitCaracteristique();
        pc.setId(pcid);
        pc.setValeur(randomString());
        pc.setCaracteristique(c);
        pc.setProduit(p);
        //Ajout du produitCaracteristique dans la Caracteristique et le po=roduit
        c.getProduits().add(pc);
        p.getCaracterisitiques().add(pc);
        return pc;
    }
    
    private ProduitCaracteristiqueDTO buildProduitCaracteristiqueDTO() {
        CaracteristiqueDTO cdto = new CaracteristiqueDTO(RANDOM.nextInt(), randomString());
        return new ProduitCaracteristiqueDTO(cdto, randomString());
    }
    
    private String randomString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < ITERATION; i++){
            builder.append((char) (RANDOM.nextInt(26) + 97));
        }
        return builder.toString();
    }
    
}
