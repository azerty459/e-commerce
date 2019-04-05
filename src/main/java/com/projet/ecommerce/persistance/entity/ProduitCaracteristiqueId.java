/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.ecommerce.persistance.entity;

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProduitCaracteristiqueId implements Serializable{
    
    @Column(name = "reference_produit")
    private String referenceProduit;
    
    @Column(name = "id_caracteristique")
    private long idCaracteristique;
    
    public ProduitCaracteristiqueId() {
        //Vide
    }
    
    public ProduitCaracteristiqueId(String reference, long id) {
        this.referenceProduit = reference;
        this.idCaracteristique = id;
    }

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public long getIdCaracteristique() {
        return idCaracteristique;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        } else if(this == o) {
            return true;
        } else if(o instanceof ProduitCaracteristiqueId) {
            ProduitCaracteristiqueId pci = (ProduitCaracteristiqueId) o;
            return this.referenceProduit.equals(pci.referenceProduit) && this.idCaracteristique == pci.idCaracteristique;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.referenceProduit, this.idCaracteristique);
    }     
    
}
