package com.projet.ecommerce.business.impl;

import java.util.Collection;

import com.projet.ecommerce.business.ILibelleBusiness;
import com.projet.ecommerce.business.dto.LibelleDTO;
import com.projet.ecommerce.business.dto.transformer.LibelleTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Libelle;
import com.projet.ecommerce.persistance.repository.LibelleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

//XXX - Transactional : org.javax ou org.springframework?
@Service
@Transactional
public class LibelleBusiness implements ILibelleBusiness {

    @Autowired
    private LibelleRepository libelleRepo;

    @Override
    public LibelleDTO add(String nom) {
        if (nom==null) return null;
        nom = nom.trim();
        if (nom.length()==0) throw new GraphQLCustomException("Le nom du libellé ne peut être vide");
        Collection<Libelle> foundLibelles = libelleRepo.findByNomIgnoreCase(nom);
        if (foundLibelles.size()>0) throw new GraphQLCustomException("Un libellé de ce nom existe déja");
        Libelle libelleAAjouter = new Libelle();
        libelleAAjouter.setNom(nom);
        return LibelleTransformer.entityToDto(libelleRepo.save(libelleAAjouter));
    }

    @Override
    public LibelleDTO get(int idLibelle) {
        final Libelle libelle = libelleRepo.findById(idLibelle).orElseThrow(
                () -> new GraphQLCustomException("Pas de libellé trouvé pour l'id : " + idLibelle));
        return LibelleTransformer.entityToDto(libelle);
    }

    @Override
    public Collection<LibelleDTO> getAll(String motCle) {
        if (motCle==null) motCle = "";
        return LibelleTransformer.entityToDto(libelleRepo.findByNomContainingIgnoreCase(motCle));
    }

    @Override
    public LibelleDTO update(int idLibelleExistant, String nouveauNom) {
        if (nouveauNom==null) throw new GraphQLCustomException("Le libellé doit avoir un nom");
        nouveauNom = nouveauNom.trim();
        if (nouveauNom==null || nouveauNom.length()==0) throw new GraphQLCustomException("Le nom du libellé ne peut être vide");
        final Libelle libelle = libelleRepo.findById(idLibelleExistant).orElseThrow(
                () -> new GraphQLCustomException("Pas de libellé trouvé pour l'id : " + idLibelleExistant));
        libelle.setNom(nouveauNom);
        return LibelleTransformer.entityToDto(libelleRepo.save(libelle));
    }

    @Override
    public void delete(int idLibelle) {
        final Libelle libelle = libelleRepo.findById(idLibelle).orElseThrow(
                () -> new GraphQLCustomException("Pas de libellé trouvé pour l'id : " + idLibelle));
        libelleRepo.deleteById(idLibelle);
        return;
    }
}
