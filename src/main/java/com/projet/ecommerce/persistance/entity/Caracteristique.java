package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

    @Id
    @Column(name="id_caracteristique")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="label")
    private String label;

    // TODO a delete
    @OneToMany(mappedBy = "caracteristique", cascade = CascadeType.ALL)
    private List<CaracteristiqueAssociated> caracteristiqueAssociatedList;

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

    public List<CaracteristiqueAssociated> getCaracteristiqueAssociatedList() {
        return caracteristiqueAssociatedList;
    }

    public void setCaracteristiqueAssociatedList(List<CaracteristiqueAssociated> caracteristiqueAssociatedList) {
        this.caracteristiqueAssociatedList = caracteristiqueAssociatedList;
    }
}
