package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

@Entity
@Table(name = "caracteristique_associated")
public class CaracteristiqueAssociated {

    @Id
    @Column(name="id_caracteristique_associated")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Caracteristique caracteristique;

    @Column
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Caracteristique getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
