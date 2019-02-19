package com.projet.ecommerce.persistance.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitRepositoryTests {

    private static final Produit TEMP_INSERT = new Produit();
    private static final Produit TEMP_DELETE = new Produit();
    private static final Produit TEMP_UPDATE = new Produit();
    private static final Produit TEMP_GET = new Produit();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setDescription("joli produit");

        TEMP_DELETE.setReferenceProduit("A05A88");
        TEMP_DELETE.setPrixHT(11.7f);
        TEMP_DELETE.setDescription("produit");

        TEMP_UPDATE.setReferenceProduit("A05A89");
        TEMP_UPDATE.setPrixHT(10.875f);
        TEMP_UPDATE.setDescription("joli truc");

        TEMP_GET.setReferenceProduit("A05A90");
        TEMP_GET.setPrixHT(9.214f);
        TEMP_GET.setDescription("bas de gamme");
    }

    @Autowired
    private ProduitRepository produitRepository;

    @Test
    public void insertProduit() {
        Assert.assertNotNull(produitRepository.save(TEMP_INSERT));
        Produit temp = produitRepository.findById(TEMP_INSERT.getReferenceProduit()).orElse(null);
        Assert.assertNotNull(temp);
    }

    @Test
    public void getProduit() {
        produitRepository.deleteAll();
        Collection<Produit> produitCollection = produitRepository.findAll();
        Assert.assertEquals(0, produitCollection.size());

        produitRepository.save(TEMP_INSERT);
        produitCollection = produitRepository.findAll();
        Assert.assertEquals(1, produitCollection.size());
    }

    @Test
    public void getProduitByID() {
        Assert.assertNotNull(produitRepository.save(TEMP_GET));
        Produit temp = produitRepository.findById(TEMP_GET.getReferenceProduit()).orElse(null);
        Assert.assertNotNull("Produit ne peut pas être null", temp);
        Assert.assertEquals(TEMP_GET.getReferenceProduit(), temp.getReferenceProduit());
        Assert.assertEquals(TEMP_GET.getPrixHT(), temp.getPrixHT(), 0);
        Assert.assertEquals(TEMP_GET.getDescription(), temp.getDescription());
    }

    @Test
    public void updateProduit() {
        produitRepository.save(TEMP_UPDATE);
        Produit temp = produitRepository.findById(TEMP_UPDATE.getReferenceProduit()).orElse(null);
        Assert.assertEquals(TEMP_UPDATE.getReferenceProduit(), temp.getReferenceProduit());
        Assert.assertEquals(TEMP_UPDATE.getPrixHT(), temp.getPrixHT(), 0);
        Assert.assertEquals(TEMP_UPDATE.getDescription(), temp.getDescription());

        TEMP_UPDATE.setReferenceProduit("A05A100");
        TEMP_UPDATE.setPrixHT(15.5f);
        TEMP_UPDATE.setDescription("joli chose");
        Assert.assertNotNull(produitRepository.save(TEMP_UPDATE));
        temp = produitRepository.findById(TEMP_UPDATE.getReferenceProduit()).orElse(null);
        Assert.assertEquals("joli chose", temp.getDescription());
        Assert.assertEquals(TEMP_UPDATE.getPrixHT(), temp.getPrixHT(), 0);
        Assert.assertEquals(TEMP_UPDATE.getReferenceProduit(), temp.getReferenceProduit());
    }

    @Test
    public void deleteProduit() {
        Assert.assertNotNull(produitRepository.save(TEMP_DELETE));
        produitRepository.delete(TEMP_DELETE);
        Assert.assertFalse(produitRepository.findById(TEMP_DELETE.getReferenceProduit()).isPresent());
    }
    
    @Test
    public void testMinNoteMoyenne() {
    	List<Produit> prods = prep2Produits();
    	Produit p1 = prods.get(0);
    	Produit p2 = prods.get(1);
    	
    	//Produits qui ont >0.5 de moyenne
    	//attendu p1, p2
    	List<Produit> produitsNoteSup1 = new ArrayList<>(produitRepository.filterProduits(0.5, null, null, null, null));
    	assertProduits(produitsNoteSup1, p1, p2);
    	
    	//Produits qui ont >2 de moyenne
    	//attendu p1
    	List<Produit> produitsNoteSup2 = new ArrayList<>(produitRepository.filterProduits(2.0, null, null, null, null));
    	assertProduits(produitsNoteSup2, p1);
    	
    	//Produits qui ont >8 de moyenne
    	//attendu aucun
    	List<Produit> produitsNoteSup3 = new ArrayList<>(produitRepository.filterProduits(8.0, null, null, null, null));
    	assertProduits(produitsNoteSup3);
    	
    	//Produits qui ont <8 de moyenne
    	//attendu p1, p2
    	List<Produit> produitsNoteInf1 = new ArrayList<>(produitRepository.filterProduits(null, 8.0, null, null, null));
    	assertProduits(produitsNoteInf1, p1, p2);
    }
    
    @Test
    public void testMaxNoteMoyenne() {
    	List<Produit> prods = prep2Produits();
    	Produit p1 = prods.get(0);
    	Produit p2 = prods.get(1);
    	
    	//Produits qui ont <8 de moyenne
    	//attendu p1, p2
    	List<Produit> produitsNoteInf1 = new ArrayList<>(produitRepository.filterProduits(null, 8.0, null, null, null));
    	assertProduits(produitsNoteInf1, p1, p2);
    	
    	//Produits qui ont <5 de moyenne
    	//attendu  p2
    	List<Produit> produitsNoteInf2 = new ArrayList<>(produitRepository.filterProduits(null, 5.0, null, null, null));
    	assertProduits(produitsNoteInf2, p2);
    	
    	//Produits qui ont <1 de moyenne
    	//attendu  aucun
    	List<Produit> produitsNoteInf3 = new ArrayList<>(produitRepository.filterProduits(null, 1.0, null, null, null));
    	assertProduits(produitsNoteInf3);
    }
    
    @Test
    public void testNomProduit() {
    	List<Produit> prods = prep2Produits();
    	Produit p1 = prods.get(0);
    	Produit p2 = prods.get(1);
    	
    	//Produits qui s'appellent "nom p1"
    	//attendu p1
    	List<Produit> produitsNom1 = new ArrayList<>(produitRepository.filterProduits(null, null, "nom p1", null, null));
    	assertProduits(produitsNom1, p1);
    	
    	//Produits qui s'appellent "nom p2"
    	//attendu p2
    	List<Produit> produitsNom2 = new ArrayList<>(produitRepository.filterProduits(null, null, "nom p2", null, null));
    	assertProduits(produitsNom2, p2);
    	
    	//Produits qui s'appellent "toto"
    	//attendu aucun
    	List<Produit> produitsNom3 = new ArrayList<>(produitRepository.filterProduits(null, null, "toto", null, null));
    	assertProduits(produitsNom3);
    }
    
    @Test
    public void testNomProduitLike() {
    	List<Produit> prods = prep2Produits();
    	Produit p1 = prods.get(0);
    	Produit p2 = prods.get(1);
    	
    	//Produits qui contient "nom p1"
    	//attendu p1
    	List<Produit> produitsNom1 = new ArrayList<>(produitRepository.filterProduits(null, null, null, "nom p1", null));
    	assertProduits(produitsNom1, p1);
    	
    	//Produits qui contient "nom p2"
    	//attendu p2
    	List<Produit> produitsNom2 = new ArrayList<>(produitRepository.filterProduits(null, null, null, "nom p2", null));
    	assertProduits(produitsNom2, p2);
    	
    	//Produits qui contient "toto"
    	//attendu aucun
    	List<Produit> produitsNom3 = new ArrayList<>(produitRepository.filterProduits(null, null, null, "toto", null));
    	assertProduits(produitsNom3);
    	
    	//Produits qui contient "nom"
    	//attendu p1, p2
    	List<Produit> produitsNom4 = new ArrayList<>(produitRepository.filterProduits(null, null, null, "nom", null));
    	assertProduits(produitsNom4, p1, p2);
    }
    
    @Test
    public void testCategorie() {
    	List<Produit> prods = prep2Produits();
    	Produit p1 = prods.get(0);
    	Produit p2 = prods.get(1);
    	
    	//Produits qui sont dans la categorie "Top Categ"
    	//attendu p1, p2
    	List<Produit> produitsCat1 = new ArrayList<>(produitRepository.filterProduits(null, null, null, null, "Top Categ"));
    	assertProduits(produitsCat1, p1, p2);
    	
    	//Produits qui sont dans la categorie "Down Categ"
    	//attendu p2
    	List<Produit> produitsCat2 = new ArrayList<>(produitRepository.filterProduits(null, null, null, null, "Down Categ"));
    	assertProduits(produitsCat2, p2);
    	
    	//Produits qui sont dans la categorie "Categ"
    	//attendu aucun
    	List<Produit> produitsCat3 = new ArrayList<>(produitRepository.filterProduits(null, null, null, null, "Categ"));
    	assertProduits(produitsCat3);
    	
    }
    
    @Test
    public void testMultiCriteres() {
    	List<Produit> prods = prep2Produits();
    	Produit p1 = prods.get(0);
    	Produit p2 = prods.get(1);
    	
    	//Produits qui sont dans la categorie "Top Categ" avec une note moyenne entre 5.0 et 9.0
    	//attendu p1
    	List<Produit> produitsMultiCrit1 = new ArrayList<>(produitRepository.filterProduits(5.0, 9.0, null, null, "Top Categ"));
    	assertProduits(produitsMultiCrit1, p1);
    	
    	//Produits qui sont dans la categorie "Down Categ" avec une note moyenne > 4.0
    	//attendu aucun
    	List<Produit> produitsMultiCrit2 = new ArrayList<>(produitRepository.filterProduits(4.0, null, null, null, "Down Categ"));
    	assertProduits(produitsMultiCrit2);
    	
    	//Produits qui sont dans la categorie "Down Categ", un nom qui contient "nom" et une note moyenne entre 0.5 et 2.0
    	//attendu p2
    	List<Produit> produitsMultiCrit3 = new ArrayList<>(produitRepository.filterProduits(0.5, 2.0, null, "nom", "Down Categ"));
    	assertProduits(produitsMultiCrit3, p2);
    	
    }
    
    private List<Produit> prep2Produits() {
    	
    	List<Produit> res = new ArrayList<>();
    	
    	//p1 7.5 de moyenne
    	// appartient à la "Top Categ"
    	Produit p1 = getDummyProduit("prod1", "nom p1");
    	AvisClient av1 = getDummyAvisClient(5);
    	AvisClient av2 = getDummyAvisClient(10);
    	p1.getAvisClients().add(av1);
    	p1.getAvisClients().add(av2);
    	av1.setProduit(p1);
    	av2.setProduit(p1);
    	
    	Categorie cat1 = getDummyCategorie("Top Categ");
    	p1.getCategories().add(cat1);
    	cat1.getProduits().add(p1);
    	
    	produitRepository.save(p1);
    	res.add(p1);
    	
    	//p2 1.0 de moyenne
    	// appartient à la "Top Categ" et à la "Down Categ"
    	Produit p2 = getDummyProduit("prod2", "nom p2");
    	AvisClient av3 = getDummyAvisClient(0);
    	AvisClient av4 = getDummyAvisClient(2);
    	p2.getAvisClients().add(av3);
    	p2.getAvisClients().add(av4);
    	av3.setProduit(p2);
    	av4.setProduit(p2);
    	
    	Categorie cat2 = getDummyCategorie("Down Categ");
    	p2.getCategories().add(cat2);
    	cat2.getProduits().add(p2);
    	p2.getCategories().add(cat1);
    	cat1.getProduits().add(p2);
    	
    	produitRepository.save(p2);
    	res.add(p2);
    	
    	return res;
    }
    
    private void assertProduits(List<Produit> res, Produit... prodAttendus) {
    	Assert.assertNotNull(res);
    	Assert.assertEquals(res.size(),prodAttendus.length);
    	
    	int index=0;
    	for(Produit p:prodAttendus) {
    		Assert.assertNotNull(res.get(index));
    		Assert.assertEquals(res.get(index++).getReferenceProduit(), p.getReferenceProduit());
    	}
    }
    
    private Produit getDummyProduit(String refProduit, String nom) {
    	Produit p = new Produit();
    	p.setReferenceProduit(refProduit);
    	p.setPrixHT(10.875f);
    	p.setDescription("DUMMY");
    	p.setNom(nom);
    	p.setCategories(new ArrayList<>());
    	p.setPhotos(new ArrayList<>());
    	p.setAvisClients(new ArrayList<>());
    	
    	return p;
    	
    }
    
    private AvisClient getDummyAvisClient(int note) {
    	AvisClient av = new AvisClient();
    	av.setDate(LocalDateTime.now());
    	av.setNote(note);
    	av.setDescription("Dummy avis");
    	
    	return av;
    	
    }
    
    private Categorie getDummyCategorie(String categorie) {
    	Categorie c = new Categorie();
    	c.setNomCategorie(categorie);
    	c.setProduits(new ArrayList<>());
    	
    	return c;
    	
    }
    
}
