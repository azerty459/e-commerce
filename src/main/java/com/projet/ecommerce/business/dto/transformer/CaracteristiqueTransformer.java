package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;


public class CaracteristiqueTransformer {

    //XXX - une caracteristique necessite un produit (non null) sinon renvoit null - correct?
    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDto, Produit produit)
    {
        if (caracteristiqueDto==null || produit == null) return null;
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setProduit(produit);
        caracteristique.setLibelle(LibelleTransformer.dtoToEntity(caracteristiqueDto.getLibelle()));
        caracteristique.setValeur(caracteristiqueDto.getValeur());
        return caracteristique;
    }

    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique)
    {
        if (caracteristique == null) return null;
        CaracteristiqueDTO caracteristiqueDto = new CaracteristiqueDTO();
        caracteristiqueDto.setLibelle(LibelleTransformer.entityToDto(caracteristique.getLibelle()));
        caracteristiqueDto.setValeur(caracteristique.getValeur());
        return caracteristiqueDto;
    }

    //XXX - why use of ArrayList necessary here?
    public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDtos, Produit produit) {
        if (caracteristiqueDtos == null || produit == null) return null;
        return caracteristiqueDtos.stream().map(d->dtoToEntity(d, produit)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static Collection<CaracteristiqueDTO> entityToDto(Collection<Caracteristique> caracteristiques) {
        if (caracteristiques==null) return null;
        return caracteristiques.stream().map(l->entityToDto(l)).collect(Collectors.toCollection(ArrayList::new));
    }
}

