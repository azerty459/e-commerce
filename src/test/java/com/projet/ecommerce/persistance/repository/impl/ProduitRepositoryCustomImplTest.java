/**
 * 
 */
package com.projet.ecommerce.persistance.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

/**
 * @author liam
 *
 */
public class ProduitRepositoryCustomImplTest {
	
	@Autowired
	private ProduitBusiness serviceProduit;

	@Autowired
	private CategorieBusiness serviceCategorie;
	
	@Test
	public void requetecriteria() {
		
		//Les categories
		Categorie categorie1 = new Categorie();
		categorie1.setNomCategorie("Informatique");
		
		Categorie categorie2 = new Categorie();
		categorie2.setNomCategorie("Composant");
		
		Categorie categorie3 = new Categorie();
		categorie3.setNomCategorie("vetements");
		
		List<Categorie> categorie_informatique = new ArrayList<>();
		categorie_informatique.add(categorie1);
		categorie_informatique.add(categorie2);
		
		List<Categorie> categorie_vetements = new ArrayList<>();
		categorie_vetements.add(categorie3);
		
		serviceCategorie.addParent(categorie1.getNomCategorie());
		serviceCategorie.addParent(categorie2.getNomCategorie());
		serviceCategorie.addParent(categorie3.getNomCategorie());
		
		//Pour le produit 1 -> Disque Dur
		AvisClient ac1 = new AvisClient();
		ac1.setNote(3);
		
		AvisClient ac2 = new AvisClient();
		ac2.setNote(8);

		List<AvisClient> listeAvisProduit1 = new ArrayList<>();
		listeAvisProduit1.add(ac1);
		listeAvisProduit1.add(ac2);
		
		Produit p1 = new Produit();
		p1.setAvisClients(listeAvisProduit1);
		p1.setNom("Disque Dur");
		p1.setCategories(categorie_informatique);
		
		//Pour le produit 2 -> T-Shirt
		AvisClient ac3 = new AvisClient();
		ac1.setNote(1);

		List<AvisClient> listeAvisProduit2 = new ArrayList<>();
		listeAvisProduit2.add(ac3);
		
		Produit p2 = new Produit();
		p2.setAvisClients(listeAvisProduit2);
		p2.setNom("T-Shirt");
		p2.setCategories(categorie_vetements);
		
		//Pour le produit 3 qui ne possede pas d'avis
		Produit p3 = new Produit();
		p3.setNom("Produit_sans_avis");
		
		List<Integer> lc = new ArrayList<>();
		lc.add(1);
		lc.add(2);
		
		serviceProduit.add("produit1", "DisqueDur", "", 45.5f, lc, null);
		
	}

}
