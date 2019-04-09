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
    
    public static ProduitCaracteristique dtoToEntity(ProduitCaracteristiqueDTO pcDto) {
        if(pcDto == null) {
            return null;
        }
        ProduitCaracteristique pc = new ProduitCaracteristique();
        pc.setCaracteristique(CaracteristiqueTransformer.dtoToEntity(pcDto.getCaracteristique()));
        pc.setId(new ProduitCaracteristiqueId(null, pc.getCaracteristique().getIdCaracteristique()));
        pc.setValeur(pcDto.getValeur());
        return pc;
    }
    
    public static Collection<ProduitCaracteristique> dtoToEntity(Iterable<ProduitCaracteristiqueDTO> pcDtoIte) {
        if(pcDtoIte == null) {
            return null;
        }
        Collection<ProduitCaracteristique> pc = new ArrayList<>();
        for(ProduitCaracteristiqueDTO pcdto : pcDtoIte) {
            pc.add(dtoToEntity(pcdto));
        }
        return pc;
    }
    
}
