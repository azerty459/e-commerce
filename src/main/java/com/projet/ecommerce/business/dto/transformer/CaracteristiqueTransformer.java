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
        if (produit == null) return null;
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setProduit(produit);
        caracteristique.setLibelle(LibelleTransformer.dtoToEntity(caracteristiqueDto.getLibelle()));
        caracteristique.setValeur(caracteristiqueDto.getValeur());
        return caracteristique;
    }

    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique)
    {
        CaracteristiqueDTO caracteristiqueDto = new CaracteristiqueDTO();
        caracteristiqueDto.setLibelle(LibelleTransformer.entityToDto(caracteristique.getLibelle()));
        caracteristiqueDto.setValeur(caracteristique.getValeur());
        return caracteristiqueDto;
    }

    //XXX - why use of ArrayList necessary here?
    public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDtos, Produit produit) {
        return caracteristiqueDtos.stream().map(d->dtoToEntity(d, produit)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static Collection<CaracteristiqueDTO> entityToDto(Collection<Caracteristique> caracteristiques) {
        return caracteristiques.stream().map(l->entityToDto(l)).collect(Collectors.toCollection(ArrayList::new));
    }
}

