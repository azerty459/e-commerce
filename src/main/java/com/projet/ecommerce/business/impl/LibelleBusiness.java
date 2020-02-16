package com.projet.ecommerce.business.impl;

import java.util.Collection;

import com.projet.ecommerce.business.ILibelleBusiness;
import com.projet.ecommerce.business.dto.LibelleDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.repository.LibelleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//XXX - Transactional : org.javax ou org.springframework?
@Service
@Transactional
public class LibelleBusiness implements ILibelleBusiness {

    @Autowired
    private LibelleRepository libelleRepo;
    //TODO
    @Override
    public LibelleDTO add(String nom) {
        if (nom==null) return null;
        if (nom.trim().length()==0) throw new GraphQLCustomException("Le nom du libellé ne peut être vide");
        libelleRepo.findByNomIgnoringCase(nom);
        return null;
    }

    @Override
    public LibelleDTO get(int idLibelle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<LibelleDTO> getAll(String motCle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LibelleDTO update(int idLibelleExistant, String nouveauNom) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(int idLibelle) {
        // TODO Auto-generated method stub

    }
}