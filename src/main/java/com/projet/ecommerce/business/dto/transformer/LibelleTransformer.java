package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.projet.ecommerce.business.dto.LibelleDTO;
import com.projet.ecommerce.persistance.entity.Libelle;

public class LibelleTransformer {

    public static Libelle dtoToEntity(LibelleDTO libelleDto)
    {
        if (libelleDto == null) return null;
        Libelle libelle = new Libelle();
        libelle.setIdLibelle(libelleDto.getId());
        libelle.setNom(libelleDto.getNom());
        return libelle;
    }

    public static LibelleDTO entityToDto(Libelle libelle)
    {
        if (libelle == null) return null;
        LibelleDTO libelleDto = new LibelleDTO();
        libelleDto.setId(libelle.getIdLibelle());
        libelleDto.setNom(libelle.getNom());
        return libelleDto;
    }

    //XXX - why use of ArrayList necessary here?
    public static Collection<Libelle> dtoToEntity(Collection<LibelleDTO> libelleDtos) {
        if (libelleDtos == null) return null;
        return libelleDtos.stream().map(d->dtoToEntity(d)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static Collection<LibelleDTO> entityToDto(Collection<Libelle> libelles) {
        if (libelles == null) return null;
        return libelles.stream().map(l->entityToDto(l)).collect(Collectors.toCollection(ArrayList::new));
    }
}

