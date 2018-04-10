package com.projet.ecommerce.dto;

import com.projet.ecommerce.entity.Caracteristique;
import com.projet.ecommerce.entity.Categorie;
import com.projet.ecommerce.entity.Photo;

import javax.persistence.*;
import java.util.List;

public class ProduitDTO {

    private String referenceProduit;

    private String nom;

    private String description;

    private double prixHT;

    private List<Categorie> categories;

    private List<Caracteristique> caracteristiques;

    private List<Photo> photos;
}
