package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    @OneToMany(mappedBy = "caracteristique", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caracteristique that = (Caracteristique) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(label, that.label) &&
                Objects.equals(caracteristiqueAssociatedList, that.caracteristiqueAssociatedList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, caracteristiqueAssociatedList);
    }
}
