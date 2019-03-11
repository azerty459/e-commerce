package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Entité représentant la table type_caracteristique sous forme de classe.
 */

@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_caracteristique")
    private int idTypeCaracteristique;
	
	@Column(name = "type_carac")
    private String type;
	
	@OneToMany(mappedBy = "typeCaracteristique", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Caracteristique> caracteristiques;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Caracteristique> getCaracteristiques() {
		return caracteristiques;
	}

	public void setCaracteristiques(List<Caracteristique> caracteristiques) {
		this.caracteristiques = caracteristiques;
	}

	public int getIdTypeCaracteristique() {
		return idTypeCaracteristique;
	}

	public void setIdTypeCaracteristique(int idTypeCaracteristique) {
		this.idTypeCaracteristique = idTypeCaracteristique;
	}
	
	
}
