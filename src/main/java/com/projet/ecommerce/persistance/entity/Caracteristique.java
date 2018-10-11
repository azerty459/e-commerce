package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

    @Id
    @Column(name="id_caracteristique")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="label")
    private String label;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
