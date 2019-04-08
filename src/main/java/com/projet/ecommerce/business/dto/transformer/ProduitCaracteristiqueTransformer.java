package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.ProduitCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristique;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristiqueId;
import java.util.ArrayList;
import java.util.Collection;


public class ProduitCaracteristiqueTransformer {
    
    public static ProduitCaracteristiqueDTO entityToDto(ProduitCaracteristique pc) {
        if(pc == null) {
            return null;
        }
        return new ProduitCaracteristiqueDTO(
                CaracteristiqueTransformer.entityToDto(pc.getCaracteristique()), 
                pc.getValeur()
        );
    }
    
    public static Collection<ProduitCaracteristiqueDTO> entityToDto(Iterable<ProduitCaracteristique> pcIte) {
        if(pcIte == null) {
            return null;
        }
        Collection<ProduitCaracteristiqueDTO> pcdto = new ArrayList<>();
        for(ProduitCaracteristique pc : pcIte) {
            pcdto.add(entityToDto(pc));
        }
        return pcdto;
    }
    
    public static ProduitCaracteristique dtoToEntity(ProduitCaracteristiqueDTO pcdto) {
        if(pcdto == null) {
            return null;
        }
        ProduitCaracteristique pc = new ProduitCaracteristique();
        pc.setCaracteristique(CaracteristiqueTransformer.dtoToEntity(pcdto.getCaracteristique()));
        pc.setId(new ProduitCaracteristiqueId(pc.getProduit().getReferenceProduit(), pc.getCaracteristique().getIdCaracteristique()));
        pc.setValeur(pcdto.getValeur());
        return pc;
    }
    
    public static Collection<ProduitCaracteristique> dtoToEntity(Iterable<ProduitCaracteristiqueDTO> pcdtoIte) {
        if(pcdtoIte == null) {
            return null;
        }
        Collection<ProduitCaracteristique> pc = new ArrayList<>();
        for(ProduitCaracteristiqueDTO pcdto : pcdtoIte) {
            pc.add(dtoToEntity(pcdto));
        }
        return pc;
    }
    
}
