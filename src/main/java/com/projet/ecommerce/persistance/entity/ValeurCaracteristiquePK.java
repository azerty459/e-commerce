package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ValeurCaracteristiquePK {
	
	@Column(name="reference_produit", nullable=false)
	private Produit referenceProduit;
	
	@Column(name="id_caracteristique", nullable=false)
	private Caracteristique idProduit;

	public Produit getReferenceProduit() {
		return referenceProduit;
	}

	public void setReferenceProduit(Produit referenceProduit) {
		this.referenceProduit = referenceProduit;
	}

	public Caracteristique getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Caracteristique idProduit) {
		this.idProduit = idProduit;
	}
	
	
}
