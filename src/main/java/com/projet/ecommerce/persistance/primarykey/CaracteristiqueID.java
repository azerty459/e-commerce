package com.projet.ecommerce.persistance.primarykey;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CaracteristiqueID implements Serializable {

    @Column(name="reference_produit")
    public String referenceProduit;

    @Column(name="id_libelle")
    public int idLibelle;

    public CaracteristiqueID() {
    }

    public CaracteristiqueID(String referenceProduit, int idLibelle) {
        this.referenceProduit = referenceProduit;
        this.idLibelle = idLibelle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idLibelle;
        result = prime * result + ((referenceProduit == null) ? 0 : referenceProduit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CaracteristiqueID other = (CaracteristiqueID) obj;
        if (idLibelle != other.idLibelle)
            return false;
        if (referenceProduit == null) {
            if (other.referenceProduit != null)
                return false;
        } else if (!referenceProduit.equals(other.referenceProduit))
            return false;
        return true;
    }
}
